package com.github.programmer3481.simple3D.engine;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AIVector3D;
import static org.lwjgl.assimp.Assimp.*;

public class ModelLoader {
    public static Mesh loadModel(String modelPath) {
        AIScene scene = aiImportFile(modelPath, aiProcess_JoinIdenticalVertices | aiProcess_Triangulate);

        if (scene == null)
            System.err.println("Couldn't load model at " + modelPath);

        AIMesh mesh = AIMesh.create(scene.mMeshes().get(0));
        int vertexCount = mesh.mNumVertices();

        AIVector3D.Buffer vertices = mesh.mVertices();
        AIVector3D.Buffer normals = mesh.mNormals();

        Vertex[] vertexList = new Vertex[vertexCount];

        for (int i = 0; i < vertexCount; i++) {
            AIVector3D vertex = vertices.get(i);
            Vector3f meshVertex = new Vector3f(vertex.x(), vertex.y(), vertex.z());

            AIVector3D normal = normals.get(i);
            Vector3f meshNormal = new Vector3f(normal.x(), normal.y(), normal.z());

            Vector2f meshTexCoord = new Vector2f(0, 0);
            if (mesh.mNumUVComponents().get(0) != 0) {
                AIVector3D texture = mesh.mTextureCoords(0).get(i);
                meshTexCoord.set(texture.x(), texture.y());
            }

            vertexList[i] = new Vertex(meshVertex, meshNormal, meshTexCoord);
        }

        int faceCount = mesh.mNumFaces();
        AIFace.Buffer indices = mesh.mFaces();
        int[] indicesList = new int[faceCount * 3];

        for (int i = 0; i < faceCount; i++) {
            AIFace face = indices.get(i);
            indicesList[i * 3+ 0] = face.mIndices().get(0);
            indicesList[i * 3+ 1] = face.mIndices().get(1);
            indicesList[i * 3+ 2] = face.mIndices().get(2);
        }

        return new Mesh(vertexList, indicesList);
    }
}
