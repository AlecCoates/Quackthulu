package com.quackthulu.boatrace2020;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HUD {
    private Boat boat;
    private TextureRegion fullDamageIcon, halfDamageIcon;
    private int damageIconWidth, damageIconHeight;

    public HUD(Boat boat, TextureRegion fullDamageIcon, TextureRegion halfDamageIcon){
        this.boat = boat;
        this.fullDamageIcon = fullDamageIcon;
        this.halfDamageIcon = halfDamageIcon;
        damageIconWidth = 64;
        damageIconHeight = 64;
    }

    public void draw(Batch batch, Viewport viewport) {
        for (int i = 0; i < boat.getHealth(); i += 2) {
            if (i < boat.getHealth() - 1) {
                batch.draw(fullDamageIcon,(i / 2) * damageIconWidth,viewport.getWorldHeight() - damageIconHeight,damageIconWidth,damageIconHeight);
            } else {
                batch.draw(halfDamageIcon,(i / 2) * damageIconWidth,viewport.getWorldHeight() - damageIconHeight,damageIconWidth,damageIconHeight);
            }
        }
    }
}
