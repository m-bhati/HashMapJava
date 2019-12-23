
public class SCBSTHashTable<K,T> implements MyHashTable_<K,T> {

	class EntryNode<K, T> {
	    EntryNode left, right;
	    K key;
	    T data;
	 
	    /* Constructor */
	    public EntryNode(K key,T obj)
	    {	
	    	this.key = key;
	        this.data = obj;
	        this.left = null;
	        this.right = null;
	    }
	    public K getKey() {
	        return this.key;
	    }

	    public void setKey(K key) {
	        this.key = key;
	    }

	    public T getData() {
	        return this.data;
	    }

	    public void setData(T obj) {
	        this.data = data;
	    }
	}

	int hashtableSize;
   	private EntryNode<K, T>[] table;
   	private int operations =0;
   	private String address = "";

	@SuppressWarnings("unchecked")
	public SCBSTHashTable(int hashtableSize) {
		this.hashtableSize = hashtableSize;
		table = new EntryNode[this.hashtableSize];
	}

	public int insert(K key, T obj) {

		this.operations = 0;
		//key should be stringSerializable      
		String ks = key.toString();

		long hashKey = hash(ks);
		long index = hashKey % hashtableSize;

		EntryNode<K,T> root = table[(int)index];
		table[(int)index] = bstInsert(root, key, obj);

		return this.operations;
	}

    private EntryNode<K,T> bstInsert(EntryNode<K,T> node, K key, T obj)
    {
        if (node == null)
            node = new EntryNode(key, obj);
        else
        {
        	// System.out.println("key compare :"+node.getKey());
            if (key.toString().compareTo(node.getKey().toString()) < 0 )
                node.left = bstInsert(node.left, key, obj);
            else
                node.right = bstInsert(node.right, key, obj);
        }
        this.operations += 1;
        return node;
    }

	// Update object for given key 
	public int update(K key, T obj) {

		this.operations =0;

		String ks = key.toString();

		long hashKey = hash(ks);
		long index = hashKey % hashtableSize;

		EntryNode<K,T> root = table[(int)index];
		root = bstUpdate(root, key, obj);

		return this.operations;
	}

    private EntryNode<K,T> bstUpdate(EntryNode<K,T> node, K key, T obj)
    {
        if (key.toString().compareTo(node.getKey().toString()) == 0) {
        	node.setData(obj);
        } else if (key.toString().compareTo(node.getKey().toString()) < 0 ) {
        	node.left = bstUpdate(node.left, key, obj);
        } else {

            node.right = bstUpdate(node.right, key, obj);
        }
        this.operations += 1;
        return node;
    }

	// Delete object for given key 
	public int delete(K key){

		this.operations =0;

		String ks = key.toString();

		long hashKey = hash(ks);
		long index = hashKey % hashtableSize;

		EntryNode<K,T> root = table[(int)index];
		root = bstdelete(root, key);
		table[(int)index] = root; 

		return this.operations;
	}

    private EntryNode<K,T> bstdelete(EntryNode<K,T> node, K key)
    {
    	this.operations += 1;
    	EntryNode<K,T> p, p2, n;
        if (key.toString().compareTo(node.getKey().toString()) == 0) {
        	EntryNode<K,T> leftTree, rightTree;
        	leftTree = node.left;
        	rightTree = node.right;

            if (leftTree == null && rightTree == null) {
                return null;
            } else if (leftTree == null) {
                p = rightTree;
                return p;
            } else if (rightTree == null) {
                p = leftTree;
                return p;
            } else {
                p2 = rightTree;
                p = rightTree;
                while (p.left != null)
                    p = p.left;
                p.left = leftTree;
                return p2;
            }
        } else if (key.toString().compareTo(node.getKey().toString()) < 0 ) {
        	n = bstdelete(node.left, key);
            node.left = n;
        } else {
        	n = bstdelete(node.right, key);
            node.right = n;  	
        }
        return node;
    } 

	// Does an object with this key exist? 
	public boolean contains(K key) {

		String ks = key.toString();

		long hashKey = hash(ks);
		long index = hashKey % hashtableSize;

		EntryNode<K,T> root = table[(int)index];

		try {
			return bstSearch(root, key);
		} catch (Exception e) {
			return false;
		}
	} 

	private boolean bstSearch(EntryNode<K,T> node, K key) throws NullPointerException {
        if (key.toString().compareTo(node.getKey().toString()) == 0) {
        	return true;
        } else if (key.toString().compareTo(node.getKey().toString()) < 0 ) {
        	return bstSearch(node.left, key);
        } else if (key.toString().compareTo(node.getKey().toString()) > 0 ){
			return bstSearch(node.right, key);
        }
        throw new NullPointerException();
    }

	// Return the object with given key 
	public T get(K key) throws NotFoundException {
		String ks = key.toString();

		long hashKey = hash(ks);
		long index = hashKey % hashtableSize;

		EntryNode<K,T> root = table[(int)index];

		try {
			return bstGet(root, key).getData();
		} catch (Exception e) {
			throw new NotFoundException();
		}
	}

	private EntryNode<K,T> bstGet(EntryNode<K,T> node, K key) throws NullPointerException {
        if (key.toString().compareTo(node.getKey().toString()) == 0) {
        	return node;
        } else if (key.toString().compareTo(node.getKey().toString()) < 0 ) {
        	return bstGet(node.left, key);
        } else if (key.toString().compareTo(node.getKey().toString()) > 0 ) {
			return bstGet(node.right, key);
        }
        throw new NullPointerException();
    } 

	// ”Address” of object with given key (explained below) 
	public String address(K key) throws NotFoundException {

		this.address="";
		String ks = key.toString();

		long hashKey = hash(ks);
		long index = hashKey % hashtableSize;

		EntryNode<K,T> root = table[(int)index];

		try {
			return Integer.toString((int)index)+"-"+bstPath(root, key);
		} catch (Exception e) {
			throw new NotFoundException();
		}
	}

	private String bstPath(EntryNode<K,T> node, K key) throws NullPointerException {
        if (key.toString().compareTo(node.getKey().toString()) == 0) {
        	return this.address;
        } else if (key.toString().compareTo(node.getKey().toString()) < 0 ) {
        	this.address = this.address+"L";
        	return bstPath(node.left, key);
        } else if (key.toString().compareTo(node.getKey().toString()) > 0 ) {
        	this.address = this.address+"R";
			return bstPath(node.right, key);
        }
        throw new NullPointerException();
    }

	public static long hash(String str) { 
	    long hash = 5381; 
	    for (int i = 0; i < str.length(); i++) { 
	        hash = ((hash << 5) + hash) + str.charAt(i); 
	    } 
	    return Math.abs(hash); 
	}
}