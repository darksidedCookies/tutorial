package de.drunkencoder.darksidedcookies.game.actor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import de.drunkencoder.darksidedcookies.framework.actor.ActorViewInterface;
import de.drunkencoder.darksidedcookies.framework.asset.TextureManager;
import de.drunkencoder.darksidedcookies.game.R;

public class AsteroidView implements ActorViewInterface
{
    protected Context context;

    protected final float[] verticesData;
    protected final short[] drawOrder;

    protected FloatBuffer texCoordBuffer;
    protected final FloatBuffer asteroidVertices;
    protected final ShortBuffer drawList;

    protected final int bytesPerFloat = 4;
    protected final int bytesPerShort = 2;
    protected final int strideBytes = 7 * bytesPerFloat;
    protected final int positionOffset = 0;
    protected final int positionDataSize = 3;
    protected final int colorOffset = 3;
    protected final int colorDataSize = 4;

    public AsteroidView(Context context)
    {
        this.context = context;

        float r = 0.0f;
        float g = 0.0f;
        float b = 1.0f;
        float a = 1.0f;

        // Eckpunkte + Farbe festlegen
        verticesData = new float[] {
                -0.2f, 0.1f, 0.0f,  // Punkt 0 Koordinaten
                r, g, b, a,         // Punkt 0 Farbe
                -0.2f, -0.1f, 0.0f, // Punkt 1 Koordinaten
                r, g, b, a,         // Punkt 1 Farbe
                0.0f, -0.1f, 0.0f,  // Punkt 2 Koordinaten
                r, g, b, a,         // Punkt 2 Farbe
                0.0f, 0.1f, 0.0f,   // Punkt 3 Koordinaten
                r, g, b, a,         // Punkt 3 Farbe
        };


        float texCoords[] = {0,1, 1,1, 0,0, 1,0};
        this.texCoordBuffer = ByteBuffer.allocateDirect(texCoords.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.texCoordBuffer.put(texCoords).position(0);

        // Form festlegen (je 3 bilden Dreieck, gegen Uhrzeigersinn)
        drawOrder = new short[]{ 0, 1, 2, 0, 2, 3 };

        // Buffer definieren (Zur Konvertierung in C++)
        asteroidVertices = ByteBuffer.allocateDirect(verticesData.length * bytesPerFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();

        asteroidVertices.put(verticesData).position(0);


        drawList = ByteBuffer.allocateDirect(drawOrder.length * bytesPerShort)
                .order(ByteOrder.nativeOrder()).asShortBuffer();

        drawList.put(drawOrder).position(0);
    }

    @Override
    public void draw(float[] matrix, int pos, int col, int mvp, int textureHandle)
    {
        // Position holen
        asteroidVertices.position(positionOffset);
        GLES20.glVertexAttribPointer(pos, positionDataSize, GLES20.GL_FLOAT, false,
                strideBytes, asteroidVertices);
        GLES20.glEnableVertexAttribArray(pos);

        asteroidVertices.position(colorOffset);
        GLES20.glVertexAttribPointer(col, colorDataSize, GLES20.GL_FLOAT, false,
                strideBytes, asteroidVertices);
        GLES20.glEnableVertexAttribArray(col);

        /*
        GLES20.glVertexAttribPointer(textureHandle, 2, GLES20.GL_FLOAT, false, 0, texCoordBuffer);
        GLES20.glEnableVertexAttribArray(textureHandle);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, TextureManager.getInstance(this.context).loadTexture(R.drawable.asteroid));
        */

        // Zeichnen
        GLES20.glUniformMatrix4fv(mvp, 1, false, matrix, 0);
        GLES20.glDrawElements(
                GLES20.GL_TRIANGLES, drawOrder.length,
                GLES20.GL_UNSIGNED_SHORT, drawList);
    }
}