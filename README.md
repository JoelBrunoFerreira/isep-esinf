# Estruturas de Informação — ESINF (2023/2024)

> **Instituto Superior de Engenharia do Porto (ISEP)**\
> 2º Ano da Licenciatura em Engenharia Informática

Repositório de exercícios práticos da unidade curricular **Estruturas de Informação**, desenvolvidos em Java com Maven. Cada package corresponde a uma estrutura de dados ou tema abordado na UC e contém as classes implementadas.

---

## 📁 Estrutura do Repositório

```
ESINF/
├── DoublyLinkedList/   Lista duplamente ligada genérica com iterador bidirecional
├── Generics/           Uso de Generics com Map, Set e coleções — Supermercado
├── Graphs/             Framework de grafos com duas representações
│   ├── map/            Grafo por lista de adjacência (MapGraph)
│   ├── matrix/         Grafo por matriz de adjacência (MatrixGraph)
│   └── (base)/         Interface Graph, CommonGraph, Edge e Algorithms
├── PQExamples/         Exemplos práticos com HeapPriorityQueue
│   ├── PrintQueue      Fila de impressão com cálculo de tempo de espera
│   └── TaskManagement  Gestão de tarefas com aging e limites de execução
├── Priority_Queue/     Implementação de fila de prioridade baseada em heap
└── Threes/             Árvores binárias de pesquisa (BST) e AVL auto-balanceada
```

---

## 📚 Conteúdo por Package

### DoublyLinkedList

Implementação genérica de uma lista duplamente ligada com nós sentinela (`header` e `trailer`), que simplificam as operações nos extremos da lista.

| Classe | Descrição |
|--------|-----------|
| `DoublyLinkedList<E>` | Lista com `addFirst`, `addLast`, `removeFirst`, `removeLast`, `equals` e `clone`. Contador `modCount` para deteção de modificações concorrentes. |
| `DoublyLinkedListIterator` | `ListIterator` bidirecional (inner class) com suporte a `next`, `previous`, `add`, `set` e `remove`, com validação de `ConcurrentModificationException`. |
| `Node<E>` | Nó interno (nested static class) com referências `prev` e `next`. |

---

### Generics

Modelação de um sistema de faturas e produtos usando as interfaces `Map`, `Set` e `List` da Java Collections Framework.

| Classe | Descrição |
|--------|-----------|
| `Invoice` | Fatura com referência e data (`LocalDate`). Implementa `Comparable` e `equals`/`hashCode` pela referência — permite uso em `HashMap` e `HashSet`. |
| `Product` | Produto com identificador, quantidade e preço. Implementa `Comparable` e `equals`/`hashCode` pelo identificador. |
| `Supermarket` | Agrega `Map<Invoice, Set<Product>>`. Oferece: leitura de faturas de uma lista de `String`, contagem de produtos por fatura, filtragem por intervalo de datas, cálculo do total de um produto, e inversão do mapa (produto → faturas). |

---

### Graphs

Framework genérica e extensível para representação e manipulação de grafos, com duas implementações concretas e um conjunto robusto de algoritmos.

#### Interface e Classe Base

| Classe / Interface | Descrição |
|--------------------|-----------|
| `Graph<V, E>` | Interface completa: vértices, arestas, graus, travessias, clonagem. |
| `CommonGraph<V, E>` | Classe abstrata com lógica partilhada: chaves numéricas (`key`/`vertex`), `copy`, `equals` independente da representação, e `hashCode`. |
| `Edge<V, E>` | Aresta com origem, destino e peso. `equals` baseado no par (origem, destino). |

#### Implementações Concretas

| Classe | Representação | Destaques |
|--------|---------------|-----------|
| `MapGraph<V, E>` | Lista de adjacência via `LinkedHashMap<V, MapVertex<V,E>>` | Eficiente para grafos esparsos; cada `MapVertex` guarda as arestas de saída. Suporta grafos dirigidos e não dirigidos. |
| `MatrixGraph<V, E>` | Matriz de adjacência `Edge<V,E>[][]` | Redimensionamento dinâmico com fator 1.5; remoção de vértice com colapso da matriz (shift de linhas/colunas). |

#### Algoritmos (`Algorithms`)

| Algoritmo | Complexidade | Descrição |
|-----------|-------------|-----------|
| `BreadthFirstSearch` | O(V + E) | BFS iterativo com fila e array de visitados. |
| `DepthFirstSearch` | O(V + E) | DFS recursivo com backtracking. |
| `allPaths` | Exponencial | Enumeração de todos os caminhos entre dois vértices com backtracking. |
| `shortestPath` / `shortestPaths` | O(V²) | Dijkstra genérico com `Comparator<E>` e `BinaryOperator<E>` — suporta qualquer tipo de peso. |
| `minDistGraph` | O(V³) | Floyd-Warshall — grafo de distâncias mínimas entre todos os pares de vértices. |

---

### Priority_Queue

Implementação completa de uma min-heap genérica, seguindo a hierarquia clássica de abstração.

| Classe / Interface | Descrição |
|--------------------|-----------|
| `PriorityQueue<K, V>` | Interface com `insert`, `min`, `removeMin`, `size` e `isEmpty`. |
| `Entry<K, V>` | Interface para o par chave-valor. |
| `AbstractPriorityQueue<K, V>` | Classe abstrata com `PQEntry` (nested class), `DefaultComparator`, validação de chaves e `isEmpty`. |
| `DefaultComparator<E>` | Comparador por delegação ao `compareTo` natural do tipo `E`. |
| `HeapPriorityQueue<K, V>` | Min-heap sobre `ArrayList` com `percolateUp`, `percolateDown` e construção bottom-up em **O(n)** via `buildHeap`. Suporta `Comparator` externo. |

---

### PQExamples

Dois exemplos práticos que demonstram a utilização da `HeapPriorityQueue` em cenários reais.

| Classe | Descrição |
|--------|-----------|
| `Document` | Documento com ID e número de páginas (nested class de `PrintQueue`). |
| `PrintQueue` | Fila de impressão baseada em `HeapPriorityQueue<Integer, Document>`. Operações: adicionar à fila, enviar para impressora, consultar próximo documento e calcular o **tempo de espera estimado** (páginas × segundos/página). |
| `Task` | Tarefa com descrição, categoria e data de criação (`LocalDateTime.now()`). |
| `TaskManagement` | Gestão de tarefas com: adição e processamento por prioridade, **reagendamento** (atualização de prioridade), mecanismo de **aging** (evita starvation ajustando prioridades de tarefas à espera há mais de T minutos), e processamento com **limite de execuções consecutivas por categoria**. |

---

### Threes — Árvores Binárias de Pesquisa

Hierarquia de árvores binárias com BST base e extensão AVL com auto-balanceamento.

| Classe / Interface | Descrição |
|--------------------|-----------|
| `BSTInterface<E>` | Contrato com `insert`, `remove`, `size`, `height`, `smallestElement`, travessias e `nodesByLevel`. |
| `BST<E>` | Árvore binária de pesquisa com `Node<E>` (nested static class). Travessias in-order (ordem crescente), pre-order e post-order. `nodesByLevel` com `HashMap<Integer, List<E>>`. `toString` com representação horizontal da árvore. |
| `AVL<E>` | Estende `BST<E>` com auto-balanceamento. Calcula o **fator de balanceamento** (altura direita − altura esquerda) e aplica rotações simples (esquerda/direita) ou duplas (Left-Right, Right-Left) após cada inserção e remoção, garantindo altura **O(log n)**. |

#### Casos de Rotação AVL

| Caso | Condição | Solução |
|------|----------|---------|
| Left-Left | BF < −1 e BF(esq) ≤ 0 | Rotação simples à direita |
| Right-Right | BF > 1 e BF(dir) ≥ 0 | Rotação simples à esquerda |
| Left-Right | BF < −1 e BF(esq) > 0 | Rotação esquerda no filho + rotação direita no nó |
| Right-Left | BF > 1 e BF(dir) < 0 | Rotação direita no filho + rotação esquerda no nó |

---

## 🛠️ Tecnologias

![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=flat&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.8+-C71A36?style=flat&logo=apachemaven&logoColor=white)
![JaCoCo](https://img.shields.io/badge/JaCoCo-Coverage-4CAF50?style=flat)

- **Java 17+**
- **Maven** — gestão de dependências e build
- **JaCoCo** — análise de cobertura de testes

---

## 🧪 Executar os Testes

```bash
# Executar todos os testes
mvn test

# Executar testes com relatório de cobertura JaCoCo
mvn test jacoco:report

# O relatório HTML fica em:
# target/site/jacoco/index.html
```

---

## 🗂️ Estruturas de Dados e Algoritmos Implementados

| Estrutura / Algoritmo | Package | Complexidade |
|---|---|---|
| **Lista Duplamente Ligada** com `ListIterator` bidirecional | DoublyLinkedList | O(1) nas extremidades |
| **Coleções Genéricas** — `Map`, `Set`, `HashMap`, `HashSet` | Generics | — |
| **Grafo por Lista de Adjacência** — `MapGraph` | Graphs/map | O(V + E) espaço |
| **Grafo por Matriz de Adjacência** — `MatrixGraph` | Graphs/matrix | O(V²) espaço |
| **BFS** — Breadth-First Search | Graphs | O(V + E) |
| **DFS** — Depth-First Search | Graphs | O(V + E) |
| **Dijkstra** — Caminho mais curto (fonte única) | Graphs | O(V²) |
| **Floyd-Warshall** — Caminhos mínimos (todos os pares) | Graphs | O(V³) |
| **Min-Heap** — Fila de Prioridade | Priority_Queue | O(log n) insert/remove |
| **Recursão** — Labirinto com backtracking | Recursion | — |
| **Recursão** — Algoritmos clássicos (MDC, palíndromo, etc.) | Recursion | — |
| **BST** — Árvore Binária de Pesquisa | Threes | O(h) médio |
| **AVL** — Árvore Auto-Balanceada | Threes | O(log n) garantido |

---

## 👤 Autor
**Joel Bruno Ferreira**