package org.esinf.Threes;

public class AVL<E extends Comparable<E>> extends BST<E> {

    /**
     * Calcula o fator de balanceamento de um nó.
     * Balance Factor = altura(subárvore direita) - altura(subárvore esquerda)
     *
     * Valores possíveis numa AVL balanceada: -1, 0, 1
     * Se |BF| > 1, o nó está desbalanceado e precisa de rotação.
     */
    private int balanceFactor(Node<E> node){
        if (node == null) {
            return 0;
        }
        return height(node.getRight()) - height(node.getLeft());
    }

    /**
     * Rotação à direita (Right Rotation)
     * Usada quando o nó está desbalanceado à esquerda (BF < -1)
     * e a subárvore esquerda está desbalanceada à esquerda (BF <= 0)
     *
     *       y                x
     *      / \              / \
     *     x   C    =>      A   y
     *    / \                  / \
     *   A   B                B   C
     */
    private Node<E> rightRotation(Node<E> node){
        Node<E> leftChild = node.getLeft();

        // Realizar rotação
        node.setLeft(leftChild.getRight());
        leftChild.setRight(node);

        return leftChild; // Nova raiz da subárvore
    }

    /**
     * Rotação à esquerda (Left Rotation)
     * Usada quando o nó está desbalanceado à direita (BF > 1)
     * e a subárvore direita está desbalanceada à direita (BF >= 0)
     *
     *     x                  y
     *    / \                / \
     *   A   y      =>      x   C
     *      / \            / \
     *     B   C          A   B
     */
    private Node<E> leftRotation(Node<E> node){
        Node<E> rightChild = node.getRight();

        // Realizar rotação
        node.setRight(rightChild.getLeft());
        rightChild.setLeft(node);

        return rightChild; // Nova raiz da subárvore
    }

    /**
     * Rotações duplas para casos mais complexos.
     *
     * Caso 1: Left-Right (BF < -1 e BF da subárvore esquerda > 0)
     *    - Primeiro: rotação à esquerda no filho esquerdo
     *    - Segundo: rotação à direita no nó
     *
     * Caso 2: Right-Left (BF > 1 e BF da subárvore direita < 0)
     *    - Primeiro: rotação à direita no filho direito
     *    - Segundo: rotação à esquerda no nó
     */
    private Node<E> twoRotations(Node<E> node){
        int bf = balanceFactor(node);

        if (bf < -1) {
            // Caso Left-Right
            node.setLeft(leftRotation(node.getLeft()));
            node = rightRotation(node);
        }
        else if (bf > 1) {
            // Caso Right-Left
            node.setRight(rightRotation(node.getRight()));
            node = leftRotation(node);
        }

        return node;
    }

    /**
     * Balancea um nó verificando o seu fator de balanceamento
     * e aplicando as rotações necessárias.
     *
     * Casos:
     * 1. BF = -2 (desbalanceado à esquerda)
     *    - Se BF(filho esquerdo) <= 0: rotação direita
     *    - Se BF(filho esquerdo) > 0: rotação dupla (left-right)
     *
     * 2. BF = 2 (desbalanceado à direita)
     *    - Se BF(filho direito) >= 0: rotação esquerda
     *    - Se BF(filho direito) < 0: rotação dupla (right-left)
     */
    private Node<E> balanceNode(Node<E> node){
        if (node == null) {
            return null;
        }

        int bf = balanceFactor(node);

        // Desbalanceado à esquerda
        if (bf < -1) {
            int leftBf = balanceFactor(node.getLeft());

            if (leftBf <= 0) {
                // Caso Left-Left: rotação simples à direita
                node = rightRotation(node);
            } else {
                // Caso Left-Right: rotação dupla
                node = twoRotations(node);
            }
        }
        // Desbalanceado à direita
        else if (bf > 1) {
            int rightBf = balanceFactor(node.getRight());

            if (rightBf >= 0) {
                // Caso Right-Right: rotação simples à esquerda
                node = leftRotation(node);
            } else {
                // Caso Right-Left: rotação dupla
                node = twoRotations(node);
            }
        }

        return node;
    }

    /**
     * Insere um elemento na árvore AVL mantendo o balanceamento.
     * Após cada inserção, verifica e corrige o balanceamento.
     */
    @Override
    public void insert(E element){
        root = insert(element, root);
    }

    private Node<E> insert(E element, Node<E> node){
        // Caso base: posição vazia encontrada
        if (node == null) {
            return new Node<>(element, null, null);
        }

        int cmp = element.compareTo(node.getElement());

        if (cmp < 0) {
            // Inserir à esquerda
            node.setLeft(insert(element, node.getLeft()));
        } else if (cmp > 0) {
            // Inserir à direita
            node.setRight(insert(element, node.getRight()));
        } else {
            // Elemento já existe, substituir
            node.setElement(element);
            return node; // Não precisa balancear
        }

        // Balancear o nó após inserção
        return balanceNode(node);
    }

    /**
     * Remove um elemento da árvore AVL mantendo o balanceamento.
     * Após cada remoção, verifica e corrige o balanceamento.
     */
    @Override
    public void remove(E element){
        root = remove(element, root());
    }

    private Node<E> remove(E element, Node<E> node) {
        if (node == null) {
            return null;
        }

        if (element.compareTo(node.getElement()) == 0) {
            // Nó a ser removido encontrado

            // Caso 1: Nó folha (sem filhos)
            if (node.getLeft() == null && node.getRight() == null) {
                return null;
            }

            // Caso 2: Apenas filho direito
            if (node.getLeft() == null) {
                return node.getRight();
            }

            // Caso 3: Apenas filho esquerdo
            if (node.getRight() == null) {
                return node.getLeft();
            }

            // Caso 4: Dois filhos
            // Substituir pelo menor elemento da subárvore direita
            E smallElem = smallestElement(node.getRight());
            node.setElement(smallElem);
            node.setRight(remove(smallElem, node.getRight()));

            // Balancear após remoção
            node = balanceNode(node);
        }
        else if (element.compareTo(node.getElement()) < 0) {
            node.setLeft(remove(element, node.getLeft()));
            node = balanceNode(node);
        }
        else {
            node.setRight(remove(element, node.getRight()));
            node = balanceNode(node);
        }

        return node;
    }

    /**
     * Verifica se duas AVL são iguais (mesma estrutura e elementos).
     */
    public boolean equals(Object otherObj) {
        if (this == otherObj)
            return true;

        if (otherObj == null || this.getClass() != otherObj.getClass())
            return false;

        AVL<E> second = (AVL<E>) otherObj;
        return equals(root, second.root);
    }

    public boolean equals(Node<E> root1, Node<E> root2) {
        if (root1 == null && root2 == null)
            return true;
        else if (root1 != null && root2 != null) {
            if (root1.getElement().compareTo(root2.getElement()) == 0) {
                return equals(root1.getLeft(), root2.getLeft())
                        && equals(root1.getRight(), root2.getRight());
            } else
                return false;
        }
        else return false;
    }
}