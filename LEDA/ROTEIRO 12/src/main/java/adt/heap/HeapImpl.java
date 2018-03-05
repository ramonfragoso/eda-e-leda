package adt.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * O comportamento de qualquer heap Ã© definido pelo heapify. Neste caso o
 * heapify dessa heap deve comparar os elementos e colocar o maior sempre no
 * topo. Ou seja, admitindo um comparador normal (responde corretamente 3 > 2),
 * essa heap deixa os elementos maiores no topo. Essa comparaÃ§Ã£o nÃ£o Ã© feita 
 * diretamente com os elementos armazenados, mas sim usando um comparator. 
 * Dessa forma, dependendo do comparator, a heap pode funcionar como uma max-heap 
 * ou min-heap.
 */
public class HeapImpl<T extends Comparable<T>> implements Heap<T> {

   protected T[] heap;
   protected int index = -1;
   /**
    * O comparador Ã© utilizado para fazer as comparaÃ§Ãµes da heap. O ideal Ã©
    * mudar apenas o comparator e mandar reordenar a heap usando esse
    * comparator. Assim os metodos da heap nÃ£o precisam saber se vai funcionar
    * como max-heap ou min-heap.
    */
   protected Comparator<T> comparator;

   private static final int INITIAL_SIZE = 20;
   private static final int INCREASING_FACTOR = 10;

   /**
    * Construtor da classe. Note que de inicio a heap funciona como uma
    * min-heap.
    */
   @SuppressWarnings("unchecked")
   public HeapImpl(Comparator<T> comparator) {
      this.heap = (T[]) (new Comparable[INITIAL_SIZE]);
      this.comparator = comparator;
   }

   // /////////////////// METODOS IMPLEMENTADOS
   private int parent(int i) {
      return (i - 1) / 2;
   }

   /**
    * Deve retornar o indice que representa o filho a esquerda do elemento
    * indexado pela posicao i no vetor
    */
   private int left(int i) {
      return (i * 2 + 1);
   }

   /**
    * Deve retornar o indice que representa o filho a direita do elemento
    * indexado pela posicao i no vetor
    */
   private int right(int i) {
      return (i * 2 + 1) + 1;
   }

   @Override
   public boolean isEmpty() {
      return (index == -1);
   }

   @Override
   public T[] toArray() {
      ArrayList<T> resp = new ArrayList<T>();
      for (T elem : this.heap) {
         resp.add(elem);
      }
      return (T[]) resp.toArray(new Comparable[0]);
   }

   // ///////////// METODOS A IMPLEMENTAR
   /**
    * Valida o invariante de uma heap a partir de determinada posicao, que pode
    * ser a raiz da heap ou de uma sub-heap. O heapify deve colocar os maiores
    * (comparados usando o comparator) elementos na parte de cima da heap.
    */
   private void heapify(int position) {
	   
	   int largest = position;
	   int l = left(largest);
	   int r = right(largest);
	   
	   if (l < size() && this.heap[l].compareTo(this.heap[largest]) < 0)
		   largest = l;
	   
	   if(r < size() && this.heap[r].compareTo(this.heap[largest]) < 0)
		   largest = r;
	   
	   System.out.println(l + " " + r + " size = " + size());
	   if(largest != position) {
		   util.Util.swap(this.heap, largest, position);
		   heapify(largest);
	   }
   }
   
   @Override
   public void insert(T element) {
      // ESSE CODIGO E PARA A HEAP CRESCER SE FOR PRECISO. NAO MODIFIQUE
      if (index == heap.length - 1) {
         heap = Arrays.copyOf(heap, heap.length + INCREASING_FACTOR);
      }
      // /////////////////////////////////////////////////////////////////
      
      this.heap[size()] = element;
      index++;
      this.siftUp();
   }
   
   private void siftUp() {
	   
	   int i = this.index;
	   while(hasParent(i) && this.heap[parent(i)].compareTo(this.heap[i]) > 0) {
		   util.Util.swap(this.heap, i, (i-1)/2);
		   i = (i-1)/2;
	   }
   }
      
   @Override
   public void buildHeap(T[] array) {
      
	   this.heap = array;
	   this.index = array.length-1;
	   for(int i = parent(array.length-1)/2; i >= 0; i--) {
		   System.out.println("i do buildheap = " + i);
		   heapify(i);
	   }
   }

   @Override
   public T extractRootElement() {
	   
	   T element = this.heap[0];
	   this.remove(0);
	   return element;
   }
   
   private void remove(int i) {
	   
	   if(this.index >= 0) {
		   util.Util.swap(this.heap, i, this.index);
		   this.index--;
		   heapify(i);
	   }
   }

   @Override
   public T rootElement() {
	   if(this.isEmpty()) return null;
	   return this.heap[0];
   }

   @Override
   public T[] heapsort(T[] array) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Not implemented yet!");
   }

   @Override
   public int size() {
      return this.heap.length;
   }

   public Comparator<T> getComparator() {
      return comparator;
   }

   public void setComparator(Comparator<T> comparator) {
      this.comparator = comparator;
   }

   public T[] getHeap() {
      return heap;
   }
   
   public boolean hasLeft(int i) {
	   return this.left(i) < this.size();
   }
   
   public boolean hasRight(int i) {
	   return this.right(i) < this.size();
   }
   
   public boolean hasParent(int i) {
	   return this.parent(i) >= 0;
   }

   public static void main(String[] args) {
	   
	   Comparator<Integer> comparator = new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				return o1-o2;
			}
	   };
	   HeapImpl<Integer> hip = new HeapImpl<Integer>(comparator);

	   Integer[] array = {5,3,6,1,8,2};
	   hip.buildHeap(array);
	   hip.extractRootElement();
	   System.out.println(Arrays.toString(hip.heap));
	   
   }

}
