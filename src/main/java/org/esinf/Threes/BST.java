package org.esinf.Threes;

import java.util.*;

public class BST<E extends Comparable<E>> implements BSTInterface<E> {

    /** Nested static class for a binary search tree node. */
    protected static class Node<E> {
        private E element;
        private Node<E> left;
        private Node<E> right;

        public Node(E e, Node<E> leftChild, Node<E> rightChild) {
            element = e;
            left = leftChild;
            right = rightChild;
        }

        public E getElement() { return element; }
        public Node<E> getLeft() { return left; }
        public Node<E> getRight() { return right; }

        public void setElement(E e) { element = e; }
        public void setLeft(Node<E> leftChild) { left = leftChild; }
        public void setRight(Node<E> rightChild) { right = rightChild; }
    }

    protected Node<E> root = null;

    public BST() {
        root = null;
    }

    protected Node<E> root() {
        return root;
    }

    public boolean isEmpty(){
        return root == null;
    }

    /**
     * Encontra o nó que contém um elemento específico.
     * Retorna null se o elemento não existir.
     */
    protected Node<E> find(Node<E> node, E element){
        if (node == null) {
            return null;
        }

        int cmp = element.compareTo(node.getElement());

        if (cmp == 0) {
            return node; // Elemento encontrado
        } else if (cmp < 0) {
            return find(node.getLeft(), element); // Procurar à esquerda
        } else {
            return find(node.getRight(), element); // Procurar à direita
        }
    }

    /**
     * Insere um elemento na árvore.
     * Se o elemento já existir, substitui-o.
     */
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
        }

        return node;
    }

    /**
     * Remove um elemento da árvore mantendo a consistência da BST.
     */
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
            E min = smallestElement(node.getRight());
            node.setElement(min);
            node.setRight(remove(min, node.getRight()));
        }
        else if (element.compareTo(node.getElement()) < 0) {
            node.setLeft(remove(element, node.getLeft()));
        }
        else {
            node.setRight(remove(element, node.getRight()));
        }

        return node;
    }

    /**
     * Retorna o número de nós na árvore.
     */
    public int size(){
        return size(root);
    }

    private int size(Node<E> node){
        if (node == null) {
            return 0;
        }
        return 1 + size(node.getLeft()) + size(node.getRight());
    }

    /**
     * Retorna a altura da árvore.
     * Altura = número de arestas no caminho mais longo da raiz até uma folha.
     * Árvore vazia tem altura -1.
     */
    public int height(){
        return height(root);
    }

    protected int height(Node<E> node){
        if (node == null) {
            return -1;
        }

        int leftHeight = height(node.getLeft());
        int rightHeight = height(node.getRight());

        return 1 + Math.max(leftHeight, rightHeight);
    }

    /**
     * Retorna o menor elemento da árvore.
     */
    public E smallestElement(){
        if (isEmpty()) {
            return null;
        }
        return smallestElement(root);
    }

    protected E smallestElement(Node<E> node){
        // O menor elemento está sempre mais à esquerda
        if (node.getLeft() == null) {
            return node.getElement();
        }
        return smallestElement(node.getLeft());
    }

    /**
     * Travessia in-order: esquerda -> raiz -> direita
     * Produz elementos em ordem crescente numa BST.
     */
    public Iterable<E> inOrder() {
        List<E> snapshot = new ArrayList<>();
        if (root != null) {
            inOrderSubtree(root, snapshot);
        }
        return snapshot;
    }

    private void inOrderSubtree(Node<E> node, List<E> snapshot) {
        if (node == null) {
            return;
        }
        inOrderSubtree(node.getLeft(), snapshot);
        snapshot.add(node.getElement());
        inOrderSubtree(node.getRight(), snapshot);
    }

    /**
     * Travessia pre-order: raiz -> esquerda -> direita
     */
    public Iterable<E> preOrder() {
        List<E> snapshot = new ArrayList<>();
        if (root != null) {
            preOrderSubtree(root, snapshot);
        }
        return snapshot;
    }

    private void preOrderSubtree(Node<E> node, List<E> snapshot) {
        if (node == null) {
            return;
        }
        snapshot.add(node.getElement());
        preOrderSubtree(node.getLeft(), snapshot);
        preOrderSubtree(node.getRight(), snapshot);
    }

    /**
     * Travessia post-order: esquerda -> direita -> raiz
     */
    public Iterable<E> posOrder() {
        List<E> snapshot = new ArrayList<>();
        if (root != null) {
            posOrderSubtree(root, snapshot);
        }
        return snapshot;
    }

    private void posOrderSubtree(Node<E> node, List<E> snapshot) {
        if (node == null) {
            return;
        }
        posOrderSubtree(node.getLeft(), snapshot);
        posOrderSubtree(node.getRight(), snapshot);
        snapshot.add(node.getElement());
    }

    /**
     * Retorna um mapa com os nós organizados por nível.
     * Nível 0 = raiz, nível 1 = filhos da raiz, etc.
     */
    public Map<Integer, List<E>> nodesByLevel(){
        Map<Integer, List<E>> result = new HashMap<>();
        processBstByLevel(root, result, 0);
        return result;
    }

    private void processBstByLevel(Node<E> node, Map<Integer, List<E>> result, int level){
        if (node == null) {
            return;
        }

        // Adicionar o elemento ao nível correspondente
        result.computeIfAbsent(level, k -> new ArrayList<>()).add(node.getElement());

        // Processar subárvores nos níveis seguintes
        processBstByLevel(node.getLeft(), result, level + 1);
        processBstByLevel(node.getRight(), result, level + 1);
    }

    /**
     * Representação em String da árvore (desenho horizontal).
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        toStringRec(root, 0, sb);
        return sb.toString();
    }

    private void toStringRec(Node<E> root, int level, StringBuilder sb){
        if(root == null)
            return;
        toStringRec(root.getRight(), level + 1, sb);
        if (level != 0){
            for(int i = 0; i < level - 1; i++)
                sb.append("|\t");
            sb.append("|-------" + root.getElement() + "\n");
        }
        else
            sb.append(root.getElement() + "\n");
        toStringRec(root.getLeft(), level + 1, sb);
    }
}