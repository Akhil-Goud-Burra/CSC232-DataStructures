package HashTable;
import java.util.Scanner;

import HashTable.LinkedList.Node;

import java.util.ArrayList;   

interface LinkedList
{

    class Node
    {
        String Key;
        String Description;
        Node Next;

        public Node(String Key , String Description)
        {
            this.Key = Key;
            this.Description = Description;
            this.Next = null;
        }
    }

    public LinkedList.Node CreateNode(String Key , String Description);

    public void Display();

}


class ComputingHash{

    private int Array_Size;

    protected ArrayList<LinkedList.Node> VerticalArray;

    public ComputingHash()
    {
        this.Array_Size = 0;
    }

    public void Set_Array_Size(int array_size)
    {
        this.Array_Size = array_size;
    }


    public ArrayList<Node> Create_Array()
    {
        if (this.Get_Array_Size() == 0) throw new IllegalArgumentException("Array Size is not defined");

        this.VerticalArray = new ArrayList<>();

        for(int i=0; i<this.Array_Size; i++)
        {
            VerticalArray.add(null);
        }

        return (this.VerticalArray);
    }

    private int Get_Array_Size()
    {
        if(this.Array_Size == 0) throw new IllegalArgumentException("Array Size is Zero");
        else return this.Array_Size;
    }

    public int Hash( String key )
    {
        int Hashed_Index = Math.abs(key.hashCode()) % (this.Get_Array_Size());

        return ( Hashed_Index );
    }

}


class SeparateChainingHash extends ComputingHash implements LinkedList {

    private LinkedList.Node Root;
    
    public SeparateChainingHash()
    {
        super();
        this.Root = null;
    }

    
    public LinkedList.Node CreateNode(String Key , String Description)
    {
        if (Key == null || Description == null)  throw new IllegalArgumentException("Key or Description cannot be null.");

        LinkedList.Node New_Node = new LinkedList.Node(Key,Description);

        return New_Node;
    }


    private LinkedList.Node Get_Last_Address()
    {
        LinkedList.Node Current_Ptr = Root;

        while(Current_Ptr.Next != null)
        {
            Current_Ptr = Current_Ptr.Next;
        }

        return Current_Ptr;
    }


    public void Seperate_Chaining(String Key , String Description)
    {
        //Step1: Creating the Node
        LinkedList.Node new_node = CreateNode(Key,Description);

        //Step2: Computing Hashed Index for the given Key
        int HashedIndex = this.Hash(Key);
        System.out.println("HashIndex: " + HashedIndex);

        //Step3: We have to [Map] the Computed HashedIndex to the [Array].
            
            //Step1: Get the Access for the Array ( VerticalArray )

            //Step2: Map the Node to the Array using HashedIndex Value

                if(VerticalArray.size() > 0)
                {
                    if(HashedIndex < VerticalArray.size())
                    {

                        if(VerticalArray.get(HashedIndex) == null )
                        {
                            VerticalArray.set(HashedIndex, new_node);
                        }
                        else // Handling Collision
                        {

                            System.out.println("Handling Collision");
                            // Step1: Find the Collision Index. ( HashedIndex )
                            
                            // Step2: Find the Address of that Array Collision Index.
                            LinkedList.Node Collision_Pointer = VerticalArray.get(HashedIndex);

                            // Step3: Traverse till last address and attach Created Node.
                            LinkedList.Node Parent_ptr = Collision_Pointer;
                            LinkedList.Node Child_ptr = Collision_Pointer;

                            if( Parent_ptr.Next == null)
                            {
                                Parent_ptr.Next = new_node;
                            }
                            else 
                            {
                                while(Child_ptr!=null)
                                {
                                    Parent_ptr = Child_ptr;
                                    Child_ptr = Child_ptr.Next;
                                }

                                if(Parent_ptr.Next == null) Parent_ptr.Next = new_node;
                                else throw new IllegalArgumentException("Parent Pointer Does Not Exists"); 
                            }
                        }
                    }
                    else
                    {
                        throw new IllegalArgumentException("HashedIndex is out of bounds");
                    }
                }
                else
                {
                    throw new IllegalArgumentException("Vertical Array Not Exist");
                }
    }


    public void Display()
    {
        LinkedList.Node Parent_Pointer = null;
        LinkedList.Node Child_Pointer = null;

        for( int i=0; i<VerticalArray.size(); i++ )
        {
            Parent_Pointer = VerticalArray.get(i);
            Child_Pointer = VerticalArray.get(i);

            while( Child_Pointer != null )
            {
                Parent_Pointer = Child_Pointer;
                Child_Pointer = Child_Pointer.Next;
                System.out.println("[ " +Parent_Pointer.Key + " " +Parent_Pointer.Description+ " ]");
            }

            System.out.println();
        }
    }
    
}



public class SeperateChaining {
    
    public static void main(String[] args) {

        SeparateChainingHash main_obj = new SeparateChainingHash();
        
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.println("Enter the Array Size for Hashing");
            int array_size = scanner.nextInt();
            
            main_obj.Set_Array_Size(array_size);

            main_obj.Create_Array();

            while(true)
            {
                System.out.println("Choose one Option from Below");
                System.out.println("Enter 1 to Insert Node");
                System.out.println("Enter 2 to Display List");
                System.out.println("Enter 3 to Exit");

                int choice = scanner.nextInt();

                scanner.nextLine(); // Consume the leftover newline

                if( choice >= 3 ) { System.out.println("Choosed To Exit"); break; }

                switch (choice) 
                {

                    case 1:
                        System.out.println("Enter Key");
                        String key = scanner.nextLine();

                        System.out.println("Enter Description");
                        String Description =  scanner.nextLine();

                        main_obj.Seperate_Chaining(key, Description);
                            
                        break;


                    case 2:
                        System.out.println();
                        main_obj.Display();
                        break;
                

                    default:
                        break;
                }

            }

        }

    }
}
