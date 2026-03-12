# 🌳 Guia BST & AVL - Conceitos Principais

## 📚 Binary Search Tree (BST)

### Propriedade Fundamental
Para cada nó na BST:
- **Todos** os elementos na subárvore esquerda são **menores**
- **Todos** os elementos na subárvore direita são **maiores**

### Complexidade Temporal
| Operação | Melhor Caso | Pior Caso | Caso Médio |
|----------|-------------|-----------|------------|
| Insert   | O(log n)    | O(n)      | O(log n)   |
| Search   | O(log n)    | O(n)      | O(log n)   |
| Remove   | O(log n)    | O(n)      | O(log n)   |
| Height   | O(n)        | O(n)      | O(n)       |

**Nota:** Pior caso O(n) ocorre quando a árvore degenera numa lista (todos os nós à direita ou à esquerda).

---

## 🔄 Travessias (Traversals)

### In-Order (Esquerda → Raiz → Direita)
- **Resultado:** Elementos em ordem crescente numa BST
- **Uso:** Imprimir elementos ordenados

```
     5
   /   \
  3     7
 / \   / \
1   4 6   9

In-Order: 1, 3, 4, 5, 6, 7, 9
```

### Pre-Order (Raiz → Esquerda → Direita)
- **Uso:** Copiar árvore, criar expressões prefixas

```
Pre-Order: 5, 3, 1, 4, 7, 6, 9
```

### Post-Order (Esquerda → Direita → Raiz)
- **Uso:** Deletar árvore, avaliar expressões pós-fixas

```
Post-Order: 1, 4, 3, 6, 9, 7, 5
```

---

## ⚖️ AVL Trees

### O que é uma AVL?
Uma BST **auto-balanceada** onde:
- Para **cada nó**, a diferença de altura entre subárvores esquerda e direita é no máximo 1
- Garante operações O(log n) sempre

### Balance Factor (BF)
```
BF(nó) = altura(subárvore direita) - altura(subárvore esquerda)
```

**Valores válidos numa AVL:** -1, 0, 1

**Se |BF| > 1:** Nó está desbalanceado → precisa rotação!

---

## 🔄 Rotações em AVL

### 1. Rotação Simples à Direita (Right Rotation)
**Quando usar:** BF = -2 e BF(filho esquerdo) ≤ 0

```
       y                    x
      / \                  / \
     x   C    ──►         A   y
    / \                      / \
   A   B                    B   C
```

### 2. Rotação Simples à Esquerda (Left Rotation)
**Quando usar:** BF = 2 e BF(filho direito) ≥ 0

```
     x                      y
    / \                    / \
   A   y      ──►         x   C
      / \                / \
     B   C              A   B
```

### 3. Rotação Dupla Left-Right
**Quando usar:** BF = -2 e BF(filho esquerdo) > 0

```
Passo 1: Left rotation no filho esquerdo
Passo 2: Right rotation no nó
```

### 4. Rotação Dupla Right-Left
**Quando usar:** BF = 2 e BF(filho direito) < 0

```
Passo 1: Right rotation no filho direito
Passo 2: Left rotation no nó
```

---

## 🎯 Casos de Remoção em BST/AVL

### Caso 1: Nó Folha (sem filhos)
```
Simplesmente remover o nó
```

### Caso 2: Nó com 1 Filho
```
Substituir o nó pelo seu único filho
```

### Caso 3: Nó com 2 Filhos
```
1. Encontrar o menor elemento da subárvore direita
   (ou maior da subárvore esquerda)
2. Substituir o elemento do nó
3. Remover o nó que continha o menor elemento
4. (AVL) Balancear
```

---

## 💡 Dicas Importantes

### BST
- ✅ Use `compareTo()` para comparar elementos
- ✅ Métodos recursivos são mais elegantes
- ✅ Caso base: `node == null`
- ✅ `height()` de nó null = -1

### AVL
- ✅ **SEMPRE** balancear após insert/remove
- ✅ Calcular BF antes de decidir rotação
- ✅ Rotações duplas são 2 rotações simples em sequência
- ✅ Verificar BF do filho para escolher rotação correta

### nodesByLevel()
- ✅ Use recursão com parâmetro `level`
- ✅ `Map.computeIfAbsent()` cria lista automaticamente se não existir
- ✅ Incrementar level ao descer na árvore

---

## 🧪 Exemplo Prático de Inserção AVL

```
Inserir: 10, 20, 30

1. Inserir 10:
      10          BF = 0 ✓

2. Inserir 20:
      10          BF = 1 ✓
       \
        20

3. Inserir 30:
      10          BF = 2 ✗ (desbalanceado!)
       \
        20        BF = 1
         \
          30

   Aplicar Left Rotation em 10:

        20         BF = 0 ✓
       /  \
      10   30      Árvore balanceada!
```

---

## 📊 Quando Usar BST vs AVL?

### BST
- ✅ Implementação mais simples
- ✅ Menos overhead em operações
- ❌ Pode degenerar em O(n)
- **Usar quando:** dados chegam aleatoriamente

### AVL
- ✅ Garantia de O(log n)
- ✅ Ideal para lookups frequentes
- ❌ Mais rotações (overhead)
- **Usar quando:** necessário garantir performance

---

## ✅ Resumo do que foi implementado:

### BST Completa:
- ✅ find() - encontra nó com elemento específico
- ✅ insert() - inserção recursiva
- ✅ remove() - remoção com 3 casos tratados
- ✅ size() - conta nós recursivamente
- ✅ height() - calcula altura (max entre subárvores + 1)
- ✅ smallestElement() - vai sempre à esquerda
- ✅ nodesByLevel() - usa recursão com Map e nível

### AVL Completa:
- ✅ balanceFactor() - calcula BF = altura direita - altura esquerda
- ✅ rightRotation() - rotação simples direita
- ✅ leftRotation() - rotação simples esquerda
- ✅ twoRotations() - rotações duplas (Left-Right e Right-Left)
- ✅ balanceNode() - decide qual rotação aplicar baseado no BF
- ✅ insert() - insere e balancea
- ✅ remove() - remove e balancea

### 🎓 Pontos-chave para o exame:
- BST: Saber que in-order produz ordem crescente
- Altura: Árvore vazia = -1, folha = 0
- Remoção: 3 casos (0, 1 ou 2 filhos)
- AVL: |BF| ≤ 1 sempre, garantindo O(log n)
- Rotações: Saber quando usar cada tipo baseado nos BF

---