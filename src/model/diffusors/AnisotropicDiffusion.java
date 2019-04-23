package model.diffusors;

public class AnisotropicDiffusion extends AbstractDiffusion {
    @Override
    protected int applyDiffusion(int pixel, int up, int down, int left, int right, double lambda, EdgeDetector bd) {
        double oldValueIJ = pixel;

        double DnIij = up - oldValueIJ;
        double DsIij = down - oldValueIJ;
        double DeIij = right - oldValueIJ;
        double DoIij = left - oldValueIJ;

        double Cnij = bd.g(DnIij);
        double Csij = bd.g(DsIij);
        double Ceij = bd.g(DeIij);
        double Coij = bd.g(DoIij);

        double resultColor = oldValueIJ
                + lambda
                * (DnIij * Cnij + DsIij * Csij + DeIij * Ceij + DoIij
                * Coij);
        if(Math.abs(pixel-resultColor) > 3){
            System.out.println(pixel+ " <> " + resultColor);
        }
        return (int)resultColor;
    }
}
