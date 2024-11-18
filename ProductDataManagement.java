import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Node {
    String productId; // Updated to String
    String name;
    String category;
    double price;
    Node left, right, parent;
    boolean color; // true for red, false for black

    public Node(String productId, String name, String category, double price) {
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
        NIL = new Node(null, null, null, 0.0);
        NIL.color = false; // NIL nodes are black
        root = NIL;
    }

    public void insert(String productId, String name, String category, double price) {
        Node newNode = new Node(productId, name, category, price);
        newNode.left = NIL;
        newNode.right = NIL;
        Node parent = null;
        Node current = root;

        while (current != NIL) {
            parent = current;
            if (productId.compareTo(current.productId) < 0) {
                current = current.left;
            } else if (productId.compareTo(current.productId) > 0) {
                current = current.right;
            } else {
                System.out.println("Product ID " + productId + " already exists. Skipping...");
                return;
            }
        }

        newNode.parent = parent;
        if (parent == null) {
            root = newNode;
        } else if (productId.compareTo(parent.productId) < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }

        newNode.color = true;
        insertFix(newNode);
    }

    private void insertFix(Node node) {
        // Implement balancing logic here (rotations and color adjustments)
        // Use Red-Black Tree rules to maintain balance
    }

    public Node search(String productId) {
        Node current = root;
        while (current != NIL && !productId.equals(current.productId)) {
            if (productId.compareTo(current.productId) < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return current == NIL ? null : current;
    }

    public void printProduct(String productId) {
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
        String filePath = "C:\\Users\\jflor\\Documents\\GitHub\\productManagementSystem\\out\\production\\productManagementSystem\\amazon-product-data.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                // Split on commas that are not within quotes
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (data.length != 4) {
                    System.out.println("Invalid line format: " + line);
                    continue; // Skip malformed lines
                }
                String productId = data[0].trim();
                String name = data[1].trim();
                String category = data[2].trim();

                try {
                    String priceString = data[3].trim();
                    if (priceString.startsWith("$")) {
                        priceString = priceString.substring(1); // Remove the '$' symbol
                    }
                    double price = Double.parseDouble(priceString);
                    tree.insert(productId, name, category, price);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid price format for product ID " + productId + ": " + data[3]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Demonstration of search queries
        System.out.println("\nSearch Results:");
        tree.printProduct("4c69b61db1fc16e7013b43fc926e502d"); // Replace with actual product ID
        tree.printProduct("exampleProductID1"); // Replace with another product ID
        tree.printProduct("exampleProductID2"); // Replace with another product ID

        // Demonstration of insertions
        System.out.println("\nInsertion Tests:");
        tree.insert("newProductID", "New Product", "Category", 99.99); // New insertion
        tree.insert("4c69b61db1fc16e7013b43fc926e502d", "Duplicate Product", "Category", 49.99); // Duplicate, should trigger error
    }
}
