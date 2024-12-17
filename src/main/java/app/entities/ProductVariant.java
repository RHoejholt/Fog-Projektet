package app.entities;

public class ProductVariant {

        private int productVariantId;
        private Product product;
        private int length;

    public ProductVariant(int productVariantId, Product product, int length) {
        this.productVariantId = productVariantId;
        this.product = product;
        this.length = length;
    }

    public int getProductVariantId() {
        return productVariantId;
    }

    public Product getProduct() {
        return product;
    }

    public int getProductId() {
        return product.getProductId();
    }

    public int getLength() {
        return length;
    }


}
