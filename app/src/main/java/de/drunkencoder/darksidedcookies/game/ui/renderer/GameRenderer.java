package de.drunkencoder.darksidedcookies.game.ui.renderer;

import android.content.Context;
import android.content.Intent;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import de.drunkencoder.darksidedcookies.engine.frame.GameFrame;
import de.drunkencoder.darksidedcookies.engine.frame.GameFrameDataInterface;
import de.drunkencoder.darksidedcookies.framework.asset.MediaManager;
import de.drunkencoder.darksidedcookies.game.loop.GameLoop;
import de.drunkencoder.darksidedcookies.framework.actor.ActorViewInterface;
import de.drunkencoder.darksidedcookies.framework.entity.EntityViewInterface;
import de.drunkencoder.darksidedcookies.framework.gameobject.GameObjectViewInterface;
import de.drunkencoder.darksidedcookies.framework.ui.renderer.BaseGameRenderer;
import de.drunkencoder.darksidedcookies.game.actor.AsteroidView;
import de.drunkencoder.darksidedcookies.game.actor.PlayerView;
import de.drunkencoder.darksidedcookies.game.ui.receiver.GameReceiver;

public class GameRenderer extends BaseGameRenderer
{
    protected int mvpMatrixHandle;
    protected int positionHandle;
    protected int colorHandle;
    protected int textureHandle;

    protected float[] modelMatrix = new float[16];
    protected float[] viewMatrix = new float[16];
    protected float[] projectionMatrix = new float[16];
    protected float[] mvpMatrix = new float[16];

    protected Map<String, EntityViewInterface> entityViews = new HashMap<>();
    protected Map<String, ActorViewInterface> actorViews = new HashMap<>();

    protected Context context;

    protected GameLoop gameLoop;
    protected GameFrame lastFrame;
    protected GameFrame currentFrame;

    public GameRenderer(Context context)
    {
        this.context = context;
        this.gameLoop = new GameLoop(this.context, new GameReceiver());
        this.currentFrame = this.gameLoop.run();

        this.addActorView("Player", new PlayerView(context));
        this.addActorView("Asteroid", new AsteroidView(context));
    }


    public GameRenderer addEntityView(String key, EntityViewInterface entityView) {
        this.entityViews.put(key, entityView);
        return this;
    }

    public GameRenderer addActorView(String key, ActorViewInterface actorView) {
        this.actorViews.put(key, actorView);
        return this;
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig eglConfig)
    {
        this.initBackground();
        this.initCamera();
        this.initShader();
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height)
    {
        GLES20.glViewport(0, 0, width, height);
        final float ratio = (float) width / height;
        final float left = -ratio;
        final float right = ratio;
        final float bottom = -1.0f;
        final float top = 1.0f;
        final float near = 1.0f;
        final float far = 10.0f;
        Matrix.frustumM(this.getProjectionMatrix(), 0, left, right, bottom, top, near, far);
    }

    @Override
    public void onDrawFrame(GL10 gl10)
    {
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

        this.lastFrame = this.currentFrame;
        this.currentFrame = this.gameLoop.update(this.lastFrame);

        if (this.currentFrame.containsKey("Player") && this.actorViews.containsKey("Player"))
        {
            GameFrameDataInterface playerData = this.currentFrame.get("Player");
            GameFrameDataInterface oldPlayerData = this.lastFrame.get("Player");

            Matrix.setIdentityM(this.modelMatrix, 0);

            if(!playerData.isOutOfScreen())
            {
                Matrix.translateM(this.modelMatrix, 0, playerData.getX(), playerData.getY(), playerData.getZ());
            } else {
                Matrix.translateM(this.modelMatrix, 0, playerData.getX(), Math.copySign(1.1f, oldPlayerData.getY()), playerData.getZ());
            }

            this.draw(this.actorViews.get("Player"));
        }

        if (this.currentFrame.containsListWithKey("Asteroid") && this.actorViews.containsKey("Asteroid"))
        {
            for(GameFrameDataInterface asteroidData : this.currentFrame.getList("Asteroid"))
            {
                Matrix.setIdentityM(modelMatrix, 0);

                if(!asteroidData.isOutOfScreen())
                {
                    Matrix.translateM(this.modelMatrix, 0, asteroidData.getX(), asteroidData.getY(), asteroidData.getZ());
                } else {
                    Matrix.translateM(this.modelMatrix, 0, asteroidData.getX(), asteroidData.getY(), asteroidData.getZ());
                }
                this.draw(this.actorViews.get("Asteroid"));
            }
        }

        if(this.currentFrame.get("Player").isCollided())
        {
            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this.context);
            Intent intent = new Intent("gameover");
            localBroadcastManager.sendBroadcast(intent);
        }

    }

    protected void initBackground()
    {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
    }

    protected void initCamera()
    {
        final float eyeX = 0.0f;
        final float eyeY = 0.0f;
        final float eyeZ = 1.5f;

        final float lookX = 0.0f;
        final float lookY = 0.0f;
        final float lookZ = -5.0f;

        final float upX = 0.0f;
        final float upY = 1.0f;
        final float upZ = 0.0f;

        Matrix.setLookAtM(this.getViewMatrix(), 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);
    }

    protected void initShader()
    {
        final String vertexShader =
                "uniform mat4 u_MVPMatrix;      \n"        // A constant representing the combined model/view/projection matrix.
                        + "attribute vec4 a_Position;     \n"        // Per-vertex position information we will pass in.
                        + "attribute vec4 a_Color;        \n"        // Per-vertex color information we will pass in.
                        + "attribute vec2 aTextureCoord;  \n"
                        + "varying vec2 vTextureCoord;    \n"
                        + "varying vec4 v_Color;          \n"        // This will be passed into the fragment shader.
                        + "void main()                    \n"        // The entry point for our vertex shader.
                        + "{                              \n"
                        + "   v_Color = a_Color;          \n"        // Pass the color through to the fragment shader.
                        + "   gl_Position = u_MVPMatrix   \n"    // gl_Position is a special variable used to store the final position.
                        + "               * a_Position;   \n"     // Multiply the vertex by the matrix to get the final point in
                        + " vTextureCoord = aTextureCoord;\n"
                        + "}                              \n";    // normalized screen coordinates.

        /*
                final String fragmentShader =
                "precision mediump float;       \n"        // Set the default precision to medium. We don't need as high of a
                        + "varying vec4 v_Color;          \n"        // This is the color from the vertex shader interpolated across the
                        + "varying vec2 vTextureCoord;    \n"
                        + "uniform sampler2D uSampler;    \n"
                        + "void main()                    \n"        // The entry point for our fragment shader.
                        + "{                              \n"
                        + "   gl_FragColor = texture2D(uSampler, vec2(vTextureCoord.s, vTextureCoord.t));     \n"        // Pass the color directly through the pipeline.
                        + "}                              \n";
         */

        final String fragmentShader =
                "precision mediump float;       \n"        // Set the default precision to medium. We don't need as high of a
                        + "varying vec4 v_Color;          \n"        // This is the color from the vertex shader interpolated across the
                        + "void main()                    \n"        // The entry point for our fragment shader.
                        + "{                              \n"
                        + "   gl_FragColor = v_Color;     \n"        // Pass the color directly through the pipeline.
                        + "}                              \n";

        int vertexShaderHandle = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        int fragmentShaderHandle = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);

        GLES20.glShaderSource(vertexShaderHandle, vertexShader);
        GLES20.glShaderSource(fragmentShaderHandle, fragmentShader);

        GLES20.glCompileShader(vertexShaderHandle);
        GLES20.glCompileShader(fragmentShaderHandle);

        int programHandle = GLES20.glCreateProgram();

        GLES20.glAttachShader(programHandle, vertexShaderHandle);
        GLES20.glAttachShader(programHandle, fragmentShaderHandle);

        GLES20.glBindAttribLocation(programHandle, 0, "a_Position");
        GLES20.glBindAttribLocation(programHandle, 1, "a_Color");
        GLES20.glBindAttribLocation(programHandle, 2, "aTextureCoord");

        GLES20.glLinkProgram(programHandle);


        this.setMVPMatrixHandle(
                GLES20.glGetUniformLocation(programHandle, "u_MVPMatrix")
        );

        this.setPositionHandle(
                GLES20.glGetAttribLocation(programHandle, "a_Position")
        );

        this.setColorHandle(
                GLES20.glGetAttribLocation(programHandle, "a_Color")
        );

        this.setTextureHandle(
                GLES20.glGetAttribLocation(programHandle, "aTextureCoord")
        );

        GLES20.glUseProgram(programHandle);
    }

    protected void setMVPMatrixHandle(int mvpMatrixHandle)
    {
        this.mvpMatrixHandle = mvpMatrixHandle;
    }

    protected void setPositionHandle(int positionHandle)
    {
        this.positionHandle = positionHandle;
    }

    protected void setColorHandle(int colorHandle)
    {
        this.colorHandle = colorHandle;
    }

    protected void setTextureHandle(int textureHandle)
    {
        this.textureHandle = textureHandle;
    }

    protected float[] getViewMatrix()
    {
        return this.viewMatrix;
    }

    protected float[] getProjectionMatrix()
    {
        return this.projectionMatrix;
    }

    protected void draw(GameObjectViewInterface gameObjectView)
    {
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, modelMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);

        gameObjectView.draw(mvpMatrix, positionHandle, colorHandle, mvpMatrixHandle, textureHandle);
    }
}
