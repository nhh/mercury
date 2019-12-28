package com.niklashanft.mercury.util;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class ImageRenderer extends MapRenderer {


    @Override
    public void render(MapView map, MapCanvas canvas, Player player) {
        int size = canvas.getCursors().size();
        for(int i = 0; i < size; i++) { }
    }


}
