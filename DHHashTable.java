
import java.lang.Math;

public class DHHashTable<K, T> implements MyHashTable_<K, T> {


   class Entry<K, T> {

      private K key;
      private T value;

      public Entry(K key, T value) {
         this.key = key;
         this.value = value;
      }

      public K getKey() {
         return key;
      }

      public void setKey(K key) {
         this.key = key;
      }

      public T getValue() {
         return value;
      }

      public void setValue(T value) {
         this.value = value;
      }
   }

   int hashtableSize;
   private Entry<K, T>[] table;

   private Entry<K, T> bufItem; // for deleted items

   @SuppressWarnings("unchecked")
   public DHHashTable(int hashtableSize) {
      this.hashtableSize = hashtableSize;
      table = new Entry[this.hashtableSize];
      bufItem = new Entry(-1,-1);
   }
   // Insert new object with given key 
   public int insert(K key, T obj) {
      //operation to perform insert an item
      int operations = 1;
      //key should be stringSerializable      
      String ks = key.toString();
      long hash1 = firstHash(ks, hashtableSize);
      long hash2 = SecondHash(ks, hashtableSize);

      long index = hash1;
      while(table[(int)index] != null && table[(int)index].getKey().toString() != "-1") {
         hash1 += hash2;
         index = hash1 % hashtableSize;
         operations += 1;
      }
      table[(int)index] = new Entry<K, T>(key, obj);;

      return operations;
   } 
 
   // Update object for given key 
   public int update(K key, T obj) {
      int operations = 1;
      String ks = key.toString();

      long hash1 = firstHash(ks, hashtableSize);
      long hash2 = SecondHash(ks, hashtableSize);
      long index = hash1;
      while(table[(int)index] != null) {
         if (table[(int)index].getKey().toString().equals(ks)) {
            table[(int)index] = new Entry<K, T>(key, obj);;
            return operations;
         }
         hash1 += hash2;
         index = hash1 % hashtableSize;
         operations += 1;
      }
      return 0;
   } 
 
   // Delete object for given key 
   public int delete(K key) {
      int operations = 1;
      String ks = key.toString();

      long hash1 = firstHash(ks, hashtableSize);
      long hash2 = SecondHash(ks, hashtableSize);
      long index = hash1;
      while(table[(int)index] != null) {
         if (table[(int)index].getKey().toString().equals(ks)) {
            table[(int)index] = bufItem;
            return operations;
         }
         hash1 += hash2;
         index = hash1 % hashtableSize;
         operations += 1;
      }
      return 0;
   }
 
   // Does an object with this key exist? 
   public boolean contains(K key) {
      String ks = key.toString();

      long hash1 = firstHash(ks, hashtableSize);
      long hash2 = SecondHash(ks, hashtableSize);
      long index = hash1;
      while(table[(int)index] != null) {
         if (table[(int)index].getKey().toString().equals(ks)) {
            return true;
         }
         hash1 += hash2;
         index = hash1 % hashtableSize;
      }
      return false;
   }
 
   // Return the object with given key 
   public T get(K key) throws NotFoundException {
      String ks = key.toString();

      long hash1 = firstHash(ks, hashtableSize);
      long hash2 = SecondHash(ks, hashtableSize);
      long index = hash1;
      while(table[(int)index] != null) {
         if (table[(int)index].getKey().toString().equals(ks)) {
            return table[(int)index].getValue();
         }
         hash1 += hash2;
         index = hash1 % hashtableSize;
      }
      throw new NotFoundException();
   } 
 
   // ”Address” of object with given key (explained below) 
   public String address(K key) throws NotFoundException {
      String ks = key.toString();

      long hash1 = firstHash(ks, hashtableSize);
      long hash2 = SecondHash(ks, hashtableSize);
      long index = hash1;
      while(table[(int)index] != null) {
         if (table[(int)index].getKey().toString().equals(ks)) {
            return ""+index;
         }
         hash1 += hash2;
         index = hash1 % hashtableSize;
      }
      throw new NotFoundException();
   }
   // 

   private long firstHash(String str, int hashtableSize) {
       long hash = 5381; 
       for (int i = 0; i < str.length(); i++) { 
         hash = ((hash << 5) + hash) + str.charAt(i); 
       } 
       return Math.abs(hash) % hashtableSize;
   }

   private long SecondHash(String str, int hashtableSize) {
      long hash = 0; 
      for (int i = 0; i < str.length(); i++) { 
         hash = str.charAt(i) + (hash << 6) + (hash << 16) - hash; 
      } 
      return Math.abs(hash) % (hashtableSize - 1) + 1;
   }
}