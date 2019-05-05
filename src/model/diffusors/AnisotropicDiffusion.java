package model.diffusors;

public class AnisotropicDiffusion extends AbstractDiffusion {
    @Override
    protected int applyDiffusion(int pixel, int up, int down, int left, int right, double lambda, EdgeDetector bd) {
        double oldValueIJ = pixel;

        double dnIij = up - oldValueIJ;
        double dsIij = down - oldValueIJ;
        double deIij = right - oldValueIJ;
        double doIij = left - oldValueIJ;

        double cnij = bd.g(dnIij);
        double cij = bd.g(dsIij);
        double ceij = bd.g(deIij);
        double coij = bd.g(doIij);

        double resultColor = oldValueIJ
                + lambda
                * (dnIij * cnij + dsIij * cij + deIij * ceij + doIij
                * coij);
        return (int)resultColor;
    }
}
