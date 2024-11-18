import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Node {
    int productId;
    String name;
    String category;
    double price;
    Node left, right, parent;
    boolean color; // true for red, false for black

    public Node(int productId, String name, String category, double price) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.color = true; // New nodes are red by default
        this.left = null;
        this.right = null;
        this.parent = null;
    }
}

class RedBlackTree {
    private Node root;
    private final Node NIL;

    public RedBlackTree() {
        NIL = new Node(-1, "", "", 0.0);
        NIL.color = false; // NIL nodes are black
        root = NIL;
    }

    public void insert(int productId, String name, String category, double price) {
        Node newNode = new Node(productId, name, category, price);
        newNode.left = NIL;
        newNode.right = NIL;
        Node parent = null;
        Node current = root;

        while (current != NIL) {
            parent = current;
            if (productId < current.productId) {
                current = current.left;
            } else if (productId > current.productId) {
                current = current.right;
            } else {
                System.out.println("Product ID " + productId + " already exists. Skipping...");
                return;
            }
        }

        newNode.parent = parent;
        if (parent == null) {
            root = newNode;
        } else if (productId < parent.productId) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }

        newNode.color = true;
        insertFix(newNode);
    }

    private void insertFix(Node node) {
        // Implement the balancing logic to ensure the tree properties
        // (Rotation and recoloring as needed)
    }

    public Node search(int productId) {
        Node current = root;
        while (current != NIL && productId != current.productId) {
            if (productId < current.productId) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return current == NIL ? null : current;
    }

    public void printProduct(int productId) {
        Node product = search(productId);
        if (product != null) {
            System.out.println("Product ID: " + product.productId);
            System.out.println("Name: " + product.name);
            System.out.println("Category: " + product.category);
            System.out.println("Price: $" + product.price);
        } else {
            System.out.println("Product not found.");
        }
    }
}

public class ProductDataManagement {
    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();
        String filePath = "/mnt/data/amazon-product-data.csv"; // Adjust path as needed

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int productId = Integer.parseInt(data[0]);
                String name = data[1];
                String category = data[2];
                double price = Double.parseDouble(data[3]);

                tree.insert(productId, name, category, price);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Example search for testing
        tree.printProduct(101); // Replace with actual product ID for demonstration
    }
}

