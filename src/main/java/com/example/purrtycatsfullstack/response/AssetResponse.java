package com.example.purrtycatsfullstack.response;

import com.example.purrtycatsfullstack.model.Asset;
import java.util.List;

public class AssetResponse {
    private List<Asset> assets;  // Use List for more flexibility

    public List<Asset> getAssets() {
        return assets;
    }

    public void setAssets(List<Asset> assets) {
        this.assets = assets;
    }
}
