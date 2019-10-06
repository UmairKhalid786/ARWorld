package com.techlad.vrsample

import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var fragment: ArFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupFragment();
    }

    private fun setupFragment() {

        fragment = sceneform_frg as ArFragment
        fragment.setOnTapArPlaneListener { hitResult, plane, motionEvent ->
            addOnFragment(hitResult.createAnchor())
        }
    }

    private fun addOnFragment(anchor: Anchor?) {
        ModelRenderable
            .builder()
            .setSource(this , Uri.parse("model.sfb"))
            .build()
            .thenAccept({ renderable ->
                addRenderableToScreen(anchor , renderable)
            })
    }

    private fun addRenderableToScreen(anchor: Anchor?, renderable: ModelRenderable?) {
        val anchorNode = AnchorNode(anchor)

        val transformableNode = TransformableNode(fragment.transformationSystem)
        transformableNode.setParent(anchorNode)
        transformableNode.renderable = renderable
        fragment.arSceneView.scene.addChild(transformableNode)

        transformableNode.select()
    }


}
