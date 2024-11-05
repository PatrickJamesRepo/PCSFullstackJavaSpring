const canvas = document.getElementById('gameCanvas');
const context = canvas.getContext('2d');

// Global Variables
let isWalletConnected = false;
let currentWalletAddress = '';
let walletAddress = null;
let rewardsAddress = null; // This will hold the rewards address after wallet connection

const boardWidth = 360;
const boardHeight = 640;

// Load images
const backgroundImgs = [
    loadImage('/images/duskdawn.png'), // DuskDawn (0)
    loadImage('/images/night.png'),     // Night (1)
    loadImage('/images/day.png'),       // Day (2)
];

// Cat images array (for selection)
const cats = {
    "Zombie": loadImage('/images/Zombie.png'),
    "Pirate": loadImage('/images/Pirate.png'),
    "Jason": loadImage('/images/Jason.png'),
    "Kitsune": loadImage('/images/Kitsune.png'),
    "Robot": loadImage('/images/Robot.png'),
    "Clown": loadImage('/images/Clown.png'),
    "Devil": loadImage('/images/Devil.png'),
    "Angel": loadImage('/images/Angel.png'),
    "Faron": loadImage('/images/Faron.png'),
    "Strawhat": loadImage('/images/Strawhat.png')
};

let selectedCatImg = cats["Robot"]; // Default cat

// Load pipes and sounds
const topPipeImg = loadImage('/images/post1.png');
const bottomPipeImg = loadImage('/images/post1.png');

const flapSound = new Audio('/sounds/flap.mp3');
const hitSound = new Audio('/sounds/hit.mp3');
const pointSound = new Audio('/sounds/point.mp3');
const dieSound = new Audio('/sounds/die.mp3');
const swooshSound = new Audio('/sounds/swoosh.mp3');

// Game Constants
const catWidth = 34;
const catHeight = 24;
let cat = { x: boardWidth / 8, y: boardHeight / 2, width: catWidth, height: catHeight };
let velocityY = 0;
const gravity = 1;

let pipes = [];
let score = 0;
let gameOver = false;

// Pipe setup
const pipeWidth = 64;
const pipeHeight = 512;
const openingSpaceFactor = 4;
let minPipeHeight = boardHeight / 8;
let maxPipeHeight = boardHeight / 2;
let openingSpace = boardHeight / openingSpaceFactor;

// Background transitions
let currentBackgroundIndex = 0; // Start with the first background
let nextBackgroundIndex = 2; // Start with the third background (0 -> 2)
let backgroundChangeTimes = [7000, 7000]; // Duration for each background state
let fadeDuration = 3000; // Duration for the fade effect (in milliseconds)
let fadeStartTime = 0;
let currentTime = 0;
let opacity = 1;
let transitioning = false;

// Loop through the background order: 0 -> 2 -> 0 -> 1
const backgroundOrder = [0, 2, 0, 1];
let currentOrderIndex = 0; // Keep track of the current position in the order

// Calculate total time for transitions
let totalTransitionTime = backgroundChangeTimes.reduce((a, b) => a + b, 0);


// Load Image Function
function loadImage(src) {
    const img = new Image();
    img.src = src;
    return img;
}

// Update Background Function
function updateBackground() {
    currentTime += 1000 / 60; // Update time for each frame (60 fps)

    let elapsedTime = currentTime % (totalTransitionTime + fadeDuration); // Include fade duration
    let fadeProgress = 0;

    // Check if we need to transition to the next background
    if (elapsedTime < totalTransitionTime) {
        // If we're not in a transition, check if we need to start one
        if (!transitioning) {
            let timePassed = backgroundChangeTimes[currentOrderIndex % backgroundChangeTimes.length];

            if (elapsedTime >= timePassed) {
                transitioning = true;
                fadeStartTime = currentTime; // Reset fade start time
                currentOrderIndex = (currentOrderIndex + 1) % backgroundOrder.length; // Move to the next in the order
                nextBackgroundIndex = backgroundOrder[currentOrderIndex]; // Get the next background
            }
        }
    } else if (transitioning) {
        // If transitioning, calculate fade progress
        fadeProgress = (currentTime - fadeStartTime) / fadeDuration;
        opacity = 1 - Math.max(0, Math.min(1, fadeProgress)); // Fade out the current background

        if (fadeProgress >= 1) {
            transitioning = false; // End the transition
            currentBackgroundIndex = nextBackgroundIndex; // Update to the new background
            opacity = 1; // Reset opacity for the new background
        }
    }
}




// Place Pipes Function
function placePipes() {
    const randomPipeHeight = Math.random() * (maxPipeHeight - minPipeHeight) + minPipeHeight;

    // Adjust the gap between the pipes based on the thickness of the new image
    pipes.push({
        x: boardWidth,
        y: randomPipeHeight - pipeHeight,
        width: pipeWidth,
        height: pipeHeight,
        passed: false
    });
    pipes.push({
        x: boardWidth,
        y: randomPipeHeight + openingSpace,
        width: pipeWidth,
        height: pipeHeight,
        passed: false
    });
}

// Game Loop
setInterval(placePipes, 1500);
setInterval(gameLoop, 1000 / 60);

function gameLoop() {
    if (!gameOver) {
        move();
        updateBackground();
        render();
    }
}

// Connect to Wallet and Initialize Game
document.addEventListener('DOMContentLoaded', async () => {
    await connectWallet(); // Connect to wallet and set walletAddress
    if (!walletAddress) {
        console.warn('Game cannot start: walletAddress is not defined');
        return;
    }
    init();
});

// Initialize the game
async function init() {
    cat.y = boardHeight / 2;
    velocityY = 0;
    pipes = [];
    score = 0;
    gameOver = false;
    render();
}

async function updateScore(score) {
    if (!currentWalletAddress) {
        console.error("Cannot update score: walletAddress is not defined");
        return;
    }

    const response = await fetch('/api/score', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ stakeId: currentWalletAddress, score: score }),
    });

    if (!response.ok) {
        console.error("Failed to save score:", response.statusText);
    } else {
        console.log(`Score for wallet ${currentWalletAddress} updated to ${score}`);
    }
}


// Move Function
async function move() {
    velocityY += gravity;
    cat.y += velocityY;
    cat.y = Math.max(cat.y, 0);

    for (const pipe of pipes) {
        pipe.x -= 4;

        if (!pipe.passed && cat.x > pipe.x + pipe.width) {
            score += 1;
            pipe.passed = true;
            pointSound.play();

            console.log(`Score incremented to: ${score}`);

            if (currentWalletAddress) {
                await updateScore(score); // This is where the score is saved
            } else {
                console.warn('Cannot update score: walletAddress is not defined');
            }
        }

        if (collision(cat, pipe)) {
            hitSound.play();
            gameOver = true;
        }
    }

    if (cat.y > boardHeight) {
        dieSound.play();
        gameOver = true;
    }
}

function collision(cat, pipe) {
    // Allow a slight buffer zone for collisions
    const bufferZone = 5; // Add a buffer zone of 5 pixels

    let catRight = cat.x + cat.width;
    let catBottom = cat.y + cat.height;
    let catLeft = cat.x;
    let catTop = cat.y;

    let pipeRight = pipe.x + pipe.width;
    let pipeBottom = pipe.y + pipe.height;
    let pipeLeft = pipe.x;
    let pipeTop = pipe.y;

    return (
        catRight > pipeLeft + bufferZone &&
        catLeft < pipeRight - bufferZone &&
        catBottom > pipeTop + bufferZone &&
        catTop < pipeBottom - bufferZone
    );
}


// Render Function
// Render Function
function render() {
    context.clearRect(0, 0, boardWidth, boardHeight);
    context.drawImage(backgroundImgs[currentBackgroundIndex], 0, 0, boardWidth, boardHeight);
    context.drawImage(selectedCatImg, cat.x, cat.y, cat.width, cat.height);

    pipes.forEach(pipe => {
        if (pipe.y < 0) {
            context.drawImage(topPipeImg, pipe.x, pipe.y, pipe.width, pipe.height);
        } else {
            context.save();
            context.translate(pipe.x + pipe.width / 2, pipe.y + pipe.height / 2);
            context.scale(1, -1);
            context.drawImage(bottomPipeImg, -pipe.width / 2, -pipe.height / 2, pipe.width, pipe.height);
            context.restore();
        }
    });

    context.fillStyle = 'white';
    context.font = '32px Arial';
    context.fillText(gameOver ? "Game Over: " + Math.floor(score) : Math.floor(score), 10, 35);
}


// Key event for jumping
document.addEventListener('keydown', (e) => {
    if (e.code === 'Space') {
        if (gameOver) {
            init();
        } else {
            velocityY = -9;
            flapSound.play();
        }
    }
});

// Add touch support
canvas.addEventListener('touchstart', (e) => {
    e.preventDefault();
    if (gameOver) {
        init();
    } else {
        velocityY = -9;
        flapSound.play();
    }
});

// Character Selection Logic
function selectCat(catName) {
    selectedCatImg = cats[catName];
    alert(`You selected the ${catName} cat!`);
}

async function connectWallet() {
    try {
        const walletAddress = await wallet.connect(); // Attempt to connect
        console.log('Attempting to connect wallet...'); // Debugging log
        console.log(`Wallet address retrieved: ${walletAddress}`); // Check the retrieved address

        const currentWalletAddress = walletAddress; // Save the current wallet address
        isWalletConnected = true;

        // Display the stake address on the screen
        document.getElementById('stakeAddress').innerText = `Stake Address: ${currentWalletAddress}`;
        console.log(`Stake Address displayed: ${currentWalletAddress}`); // Confirm display
    } catch (error) {
        console.error('Error connecting to wallet:', error);
    }
}


document.addEventListener('DOMContentLoaded', async () => {
    await connectWallet(); // Ensure this is being called
    if (!walletAddress) {
        console.warn('Game cannot start: walletAddress is not defined');
        return;
    }
    init();
});


let gameStarted = false;
let startDelay = 2000; // 2 seconds delay

// Start Game
function startGame() {
    setTimeout(() => {
    }, startDelay);

    const playerName = document.getElementById('playerName').value;

    if (playerName) {
        alert(`Welcome ${playerName}! Game starting...`);
        document.getElementById('characterSelection').style.display = 'none';
        init();
    } else {
        alert('Please enter your name!');
    }
}
