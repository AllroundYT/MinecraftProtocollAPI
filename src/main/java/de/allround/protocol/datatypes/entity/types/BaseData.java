package de.allround.protocol.datatypes.entity.types;

public class BaseData {
    private boolean onFire, crouching, sprinting, swimming, invisible, glowing, flyingWithElytra;

    public boolean isOnFire() {
        return onFire;
    }

    public BaseData setOnFire(boolean onFire) {
        this.onFire = onFire;
        return this;
    }

    public boolean isCrouching() {
        return crouching;
    }

    public BaseData setCrouching(boolean crouching) {
        this.crouching = crouching;
        return this;
    }

    public boolean isSprinting() {
        return sprinting;
    }

    public BaseData setSprinting(boolean sprinting) {
        this.sprinting = sprinting;
        return this;
    }

    public boolean isSwimming() {
        return swimming;
    }

    public BaseData setSwimming(boolean swimming) {
        this.swimming = swimming;
        return this;
    }

    public boolean isInvisible() {
        return invisible;
    }

    public BaseData setInvisible(boolean invisible) {
        this.invisible = invisible;
        return this;
    }

    public boolean isGlowing() {
        return glowing;
    }

    public BaseData setGlowing(boolean glowing) {
        this.glowing = glowing;
        return this;
    }

    public boolean isFlyingWithElytra() {
        return flyingWithElytra;
    }

    public BaseData setFlyingWithElytra(boolean flyingWithElytra) {
        this.flyingWithElytra = flyingWithElytra;
        return this;
    }
}
