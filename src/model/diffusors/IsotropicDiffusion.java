package model.diffusors;

public class IsotropicDiffusion extends AbstractDiffusion {
    @Override
    protected int applyDiffusion(int pixel, int up, int down, int left, int right,
                                          double lambda, EdgeDetector bd) {
        double oldValueIJ = pixel;

        double DnIij = up - oldValueIJ;
        double DsIij = down - oldValueIJ;
        double DeIij = right - oldValueIJ;
        double DoIij = left - oldValueIJ;

        double Cnij = 1;
        double Csij = 1;
        double Ceij = 1;
        double Coij = 1;

        double resultColor = oldValueIJ
                + 0.25
                * (DnIij * Cnij + DsIij * Csij + DeIij * Ceij + DoIij
                * Coij);
        return (int)resultColor;
    }

}
