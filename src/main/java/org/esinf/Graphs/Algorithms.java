package org.esinf.Graphs;

import org.esinf.Graphs.matrix.MatrixGraph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.BinaryOperator;

public class Algorithms {

    /** Performs breadth-first search of a Graph starting in a vertex
     *
     * @param g Graph instance
     * @param vert vertex that will be the source of the search
     * @return a LinkedList with the vertices of breadth-first search
     */
    public static <V, E> LinkedList<V> BreadthFirstSearch(Graph<V, E> g, V vert) {
        if (!g.validVertex(vert))
            return null;

        LinkedList<V> qbfs = new LinkedList<>();
        Queue<V> queue = new LinkedList<>();
        boolean[] visited = new boolean[g.numVertices()];

        queue.add(vert);
        visited[g.key(vert)] = true;
        qbfs.add(vert);

        while (!queue.isEmpty()) {
            V current = queue.poll();

            for (V adj : g.adjVertices(current)) {
                int adjKey = g.key(adj);
                if (!visited[adjKey]) {
                    visited[adjKey] = true;
                    queue.add(adj);
                    qbfs.add(adj);
                }
            }
        }

        return qbfs;
    }

    /** Performs depth-first search starting in a vertex
     *
     * @param g Graph instance
     * @param vOrig vertex of graph g that will be the source of the search
     * @param visited set of previously visited vertices
     * @param qdfs return LinkedList with vertices of depth-first search
     */
    private static <V, E> void DepthFirstSearch(Graph<V, E> g, V vOrig, boolean[] visited, LinkedList<V> qdfs) {
        int vOrigKey = g.key(vOrig);
        visited[vOrigKey] = true;
        qdfs.add(vOrig);

        for (V adj : g.adjVertices(vOrig)) {
            int adjKey = g.key(adj);
            if (!visited[adjKey]) {
                DepthFirstSearch(g, adj, visited, qdfs);
            }
        }
    }

    /** Performs depth-first search starting in a vertex
     *
     * @param g Graph instance
     * @param vert vertex of graph g that will be the source of the search

     * @return a LinkedList with the vertices of depth-first search
     */
    public static <V, E> LinkedList<V> DepthFirstSearch(Graph<V, E> g, V vert) {
        if (!g.validVertex(vert))
            return null;

        LinkedList<V> qdfs = new LinkedList<>();
        boolean[] visited = new boolean[g.numVertices()];

        DepthFirstSearch(g, vert, visited, qdfs);

        return qdfs;
    }

    /** Returns all paths from vOrig to vDest
     *
     * @param g       Graph instance
     * @param vOrig   Vertex that will be the source of the path
     * @param vDest   Vertex that will be the end of the path
     * @param visited set of discovered vertices
     * @param path    stack with vertices of the current path (the path is in reverse order)
     * @param paths   ArrayList with all the paths (in correct order)
     */
    private static <V, E> void allPaths(Graph<V, E> g, V vOrig, V vDest, boolean[] visited,
                                        LinkedList<V> path, ArrayList<LinkedList<V>> paths) {
        int vOrigKey = g.key(vOrig);

        // Marcar vértice atual como visitado e adicionar ao caminho
        visited[vOrigKey] = true;
        path.push(vOrig);

        // Se chegámos ao destino, guardamos o caminho
        if (vOrig.equals(vDest)) {
            // Criar cópia do caminho na ordem correta
            LinkedList<V> completePath = new LinkedList<>();
            for (int i = path.size() - 1; i >= 0; i--) {
                completePath.add(path.get(i));
            }
            paths.add(completePath);
        } else {
            // Explorar todos os vértices adjacentes
            for (V adj : g.adjVertices(vOrig)) {
                int adjKey = g.key(adj);

                // Se não foi visitado, continuar a exploração
                if (!visited[adjKey]) {
                    allPaths(g, adj, vDest, visited, path, paths);
                }
            }
        }

        // Backtracking: remover vértice do caminho e marcar como não visitado
        path.pop();
        visited[vOrigKey] = false;
    }

    /** Returns all paths from vOrig to vDest
     *
     * @param g     Graph instance
     * @param vOrig information of the Vertex origin
     * @param vDest information of the Vertex destination
     * @return paths ArrayList with all paths from vOrig to vDest
     */
    public static <V, E> ArrayList<LinkedList<V>> allPaths(Graph<V, E> g, V vOrig, V vDest) {
        if (!g.validVertex(vOrig) || !g.validVertex(vDest))
            return null;

        ArrayList<LinkedList<V>> paths = new ArrayList<>();
        boolean[] visited = new boolean[g.numVertices()];
        LinkedList<V> path = new LinkedList<>();

        allPaths(g, vOrig, vDest, visited, path, paths);

        return paths;
    }

    /**
     * Computes shortest-path distance from a source vertex to all reachable
     * vertices of a graph g with non-negative edge weights
     * This implementation uses Dijkstra's algorithm
     *
     * @param g        Graph instance
     * @param vOrig    Vertex that will be the source of the path
     * @param visited  set of previously visited vertices
     * @param pathKeys minimum path vertices keys
     * @param dist     minimum distances
     */
    private static <V, E> void shortestPathDijkstra(Graph<V, E> g, V vOrig,
                                                    Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                                    boolean[] visited, V[] pathKeys, E[] dist) {
        int numVerts = g.numVertices();
        int vOrigKey = g.key(vOrig);

        // Inicializar arrays
        for (int i = 0; i < numVerts; i++) {
            dist[i] = zero;
            pathKeys[i] = null;
            visited[i] = false;
        }

        dist[vOrigKey] = zero;
        pathKeys[vOrigKey] = vOrig;

        while (true) {
            // Encontrar o vértice não visitado com menor distância
            V vMin = null;
            int vMinKey = -1;
            E minDist = null;

            for (int i = 0; i < numVerts; i++) {
                if (!visited[i] && pathKeys[i] != null) {
                    if (minDist == null || ce.compare(dist[i], minDist) < 0) {
                        minDist = dist[i];
                        vMinKey = i;
                        vMin = g.vertex(i);
                    }
                }
            }

            if (vMin == null)
                break;

            visited[vMinKey] = true;

            // Relaxar arestas adjacentes
            for (Edge<V, E> edge : g.outgoingEdges(vMin)) {
                V vAdj = edge.getVDest();
                int vAdjKey = g.key(vAdj);

                if (!visited[vAdjKey]) {
                    E newDist = sum.apply(dist[vMinKey], edge.getWeight());

                    if (pathKeys[vAdjKey] == null || ce.compare(newDist, dist[vAdjKey]) < 0) {
                        dist[vAdjKey] = newDist;
                        pathKeys[vAdjKey] = vMin;
                    }
                }
            }
        }
    }

   
    /** Shortest-path between two vertices
     *
     * @param g graph
     * @param vOrig origin vertex
     * @param vDest destination vertex
     * @param ce comparator between elements of type E
     * @param sum sum two elements of type E
     * @param zero neutral element of the sum in elements of type E
     * @param shortPath returns the vertices which make the shortest path
     * @return if vertices exist in the graph and are connected, true, false otherwise
     */
    public static <V, E> E shortestPath(Graph<V, E> g, V vOrig, V vDest,
                                        Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                        LinkedList<V> shortPath) {
        if (!g.validVertex(vOrig) || !g.validVertex(vDest))
            return null;

        int numVerts = g.numVertices();
        boolean[] visited = new boolean[numVerts];
        @SuppressWarnings("unchecked")
        V[] pathKeys = (V[]) new Object[numVerts];
        @SuppressWarnings("unchecked")
        E[] dist = (E[]) new Object[numVerts];

        shortestPathDijkstra(g, vOrig, ce, sum, zero, visited, pathKeys, dist);

        int vDestKey = g.key(vDest);

        if (pathKeys[vDestKey] == null)
            return null;

        getPath(g, vOrig, vDest, pathKeys, shortPath);

        return dist[vDestKey];
    }

    /** Shortest-path between a vertex and all other vertices
     *
     * @param g graph
     * @param vOrig start vertex
     * @param ce comparator between elements of type E
     * @param sum sum two elements of type E
     * @param zero neutral element of the sum in elements of type E
     * @param paths returns all the minimum paths
     * @param dists returns the corresponding minimum distances
     * @return if vOrig exists in the graph true, false otherwise
     */
    public static <V, E> boolean shortestPaths(Graph<V, E> g, V vOrig,
                                               Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                               ArrayList<LinkedList<V>> paths, ArrayList<E> dists) {
        if (!g.validVertex(vOrig))
            return false;

        int numVerts = g.numVertices();
        boolean[] visited = new boolean[numVerts];
        @SuppressWarnings("unchecked")
        V[] pathKeys = (V[]) new Object[numVerts];
        @SuppressWarnings("unchecked")
        E[] dist = (E[]) new Object[numVerts];

        shortestPathDijkstra(g, vOrig, ce, sum, zero, visited, pathKeys, dist);

        paths.clear();
        dists.clear();

        for (int i = 0; i < numVerts; i++) {
            LinkedList<V> path = new LinkedList<>();

            if (pathKeys[i] != null) {
                V vDest = g.vertex(i);
                getPath(g, vOrig, vDest, pathKeys, path);
                paths.add(path);
                dists.add(dist[i]);
            } else {
                paths.add(null);
                dists.add(null);
            }
        }

        return true;
    }

    /**
     * Extracts from pathKeys the minimum path between voInf and vdInf
     * The path is constructed from the end to the beginning
     *
     * @param g        Graph instance
     * @param vOrig    information of the Vertex origin
     * @param vDest    information of the Vertex destination
     * @param pathKeys minimum path vertices keys
     * @param path     stack with the minimum path (correct order)
     */
    private static <V, E> void getPath(Graph<V, E> g, V vOrig, V vDest,
                                       V[] pathKeys, LinkedList<V> path) {
        if (!vOrig.equals(vDest)) {
            path.push(vDest);

            int vDestKey = g.key(vDest);
            V predecessor = pathKeys[vDestKey];

            if (predecessor != null) {
                getPath(g, vOrig, predecessor, pathKeys, path);
            }
        } else {
            path.push(vOrig);
        }
    }

    /** Calculates the minimum distance graph using Floyd-Warshall
     * 
     * @param g initial graph
     * @param ce comparator between elements of type E
     * @param sum sum two elements of type E
     * @return the minimum distance graph
     */
    public static <V,E> MatrixGraph<V,E> minDistGraph(Graph<V,E> g, Comparator<E> ce, BinaryOperator<E> sum) {
        int numVerts = g.numVertices();

        // Criar grafo com matriz de adjacências
        MatrixGraph<V, E> result = new MatrixGraph<>(g.isDirected(), numVerts);

        // Adicionar todos os vértices
        for (V vertex : g.vertices()) {
            result.addVertex(vertex);
        }

        // Inicializar matriz com as arestas existentes
        for (Edge<V, E> edge : g.edges()) {
            result.addEdge(edge.getVOrig(), edge.getVDest(), edge.getWeight());
        }

        // Algoritmo Floyd-Warshall
        for (int k = 0; k < numVerts; k++) {
            for (int i = 0; i < numVerts; i++) {
                if (i == k || result.edge(i, k) == null)
                    continue;

                for (int j = 0; j < numVerts; j++) {
                    if (j == k || j == i || result.edge(k, j) == null)
                        continue;

                    Edge<V, E> edgeIK = result.edge(i, k);
                    Edge<V, E> edgeKJ = result.edge(k, j);

                    // Calcular distância através de k
                    E distanceThroughK = sum.apply(edgeIK.getWeight(), edgeKJ.getWeight());

                    Edge<V, E> edgeIJ = result.edge(i, j);

                    // Se não existe aresta direta ou se o caminho através de k é menor
                    if (edgeIJ == null) {
                        V vOrig = result.vertex(i);
                        V vDest = result.vertex(j);
                        result.addEdge(vOrig, vDest, distanceThroughK);
                    } else if (ce.compare(distanceThroughK, edgeIJ.getWeight()) < 0) {
                        edgeIJ.setWeight(distanceThroughK);
                    }
                }
            }
        }

        return result;
    }

}