package xyz.wagyourtail.soundcategories.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.lwjgl.opengl.GL11;

import java.util.function.DoubleConsumer;

public class SliderWidget extends ButtonWidget {
    public double value = 1.0;
    public boolean dragging = false;
    private DoubleConsumer setter;
    private String messagePart;

    public SliderWidget(int i, int j, int k, String string, double f, DoubleConsumer setter) {
        super(i, j, k, 150, 20, string);
        this.value = f;
        this.setter = setter;
        this.messagePart = string;
    }

    @Override
    protected int getYImage(boolean hovered) {
        return 0;
    }

    @Override
    protected void renderBackground(Minecraft minecraft, int i, int j) {
        if (this.visible) {
            if (this.dragging) {
                this.value = (float) (i - (this.x + 4)) / (float) (this.width - 8);
                if (this.value < 0.0F) {
                    this.value = 0.0F;
                }

                if (this.value > 1.0F) {
                    this.value = 1.0F;
                }

                this.setter.accept(this.value);
                this.message = this.messagePart + ": " + Math.round(value * 100) + "%";
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexture(this.x + (int) (this.value * (float) (this.width - 8)), this.y, 0, 66, 4, 20);
            this.drawTexture(this.x + (int) (this.value * (float) (this.width - 8)) + 4, this.y, 196, 66, 4, 20);
        }
    }

    @Override
    public boolean isMouseOver(Minecraft minecraft, int i, int j) {
        if (super.isMouseOver(minecraft, i, j)) {
            this.value = (float) (i - (this.x + 4)) / (float) (this.width - 8);
            if (this.value < 0.0F) {
                this.value = 0.0F;
            }

            if (this.value > 1.0F) {
                this.value = 1.0F;
            }

            this.setter.accept(this.value);
            this.message = this.messagePart + ": " + Math.round(value * 100) + "%";
            this.dragging = true;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        this.dragging = false;
    }
}
