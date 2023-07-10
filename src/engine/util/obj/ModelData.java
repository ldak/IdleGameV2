package engine.util.obj;

import java.util.Arrays;

public class ModelData {
 
    private float[] vertices;
    private float[] normals;
    private int[] indices;

    @Deprecated
    public ModelData(float[] vertices, float[] textureCoords, float[] normals, int[] indices,
            float furthestPoint) {
        this.vertices = vertices;
        this.normals = normals;
        this.indices = indices;
    }

    public ModelData(float[] vertices, float[] normals, int[] indices) {
        this.vertices = vertices;
        this.normals = normals;
        this.indices = indices;
    }

    public ModelData(float[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
    }
 
    public float[] getVertices() {
        return vertices;
    }

    public float[] getNormals() {
        return normals;
    }

    public int[] getIndices() {
        return indices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelData modelData = (ModelData) o;
        return Arrays.equals(vertices, modelData.vertices) && Arrays.equals(indices, modelData.indices);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(vertices);
        result = 31 * result + Arrays.hashCode(indices);
        return result;
    }
}