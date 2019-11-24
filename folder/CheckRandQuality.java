//Author: MoriatyK95
//Date: 11/1/2019
//Description: bst CheckRandQuality
//INVOKE THIS PROGRAM VIA COMMANDLINE
/*
 */


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class CheckRandQuality {
    //fields
    BST tree;

    //note! entry starts from 1!
    //keep track of entry, initialize at 0;
    //entry also represents total number of nodes (including duplicates) in BST
    int entry = 0;

    //sum of all distinct nodes
    double sum = 0;

    int totalNodes = 0;

    //sum all of nodes (including duplicates)
    double sumAll = 0;

    //only called after entry has been calculated in main (or else error due to division by 0)

    double sigma = 0;




    //constructor
    public CheckRandQuality() {
        tree = new BST();
    }

    public void sum() {
        tree.InOrder(tree.overallRoot);
    }

    public void getTotalNodes() {
        tree.countNodes(tree.overallRoot);
    }

    public void SumAllValue(){

        tree.InOrderTotal(tree.overallRoot);


    }

    public void getSigma(double meanX){
        tree.InOrderStandardDeviation(tree.overallRoot, meanX);
    }


    public double calcSD(){
        return Math.sqrt(sigma/(entry - 1));
    }




    public static void main(String[] args) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        CheckRandQuality randQuality = new CheckRandQuality();


        //parsing argument via command line in to an array
        String[] arguments = new String[args.length];

        try{

            for(int i = 0 ; i < args.length; i ++){
                arguments[i] = args[i];
            }

        }catch (NumberFormatException nfe){
            System.out.println("invalid commandline input");
        }



        try (Scanner scanner = new Scanner(new FileReader(arguments[0]))) {
            scanner.useDelimiter(" ");
            //keeping track of entries
            int entry = 0;


            //read file checked
            while (scanner.hasNext()) {
                String input = scanner.nextLine();
                randQuality.entry++;
                //System.out.println(input);

                //check if BST contain this data
                //if yes, get the node with that value and modify its arraylist
                //insert at index = current entry with element of the input value
                if (! randQuality.tree.hasValue(Integer.parseInt(input)) ){
                    //initial entry
                    randQuality.tree.insert(Integer.parseInt(input));
                    randQuality.tree.overallRoot.getNode(Integer.parseInt(input)).arrayList.add(randQuality.entry);


                } else {

                    randQuality.tree.getThisNode(Integer.parseInt(input)).arrayList.add(randQuality.entry);

                }

            }

            //if argument after filename is analyze, calculate mean node value, mean all node value, and standard deviation
            if(arguments[1].equals("analyze")){
                randQuality.sum();
                randQuality.getTotalNodes();
                randQuality.SumAllValue();
                double meanX = randQuality.sumAll / randQuality.entry;
                double mean = randQuality.sum / randQuality.totalNodes;
                randQuality.getSigma(meanX);
                double sd = randQuality.calcSD();
                System.out.println("mean node value " + String.format("%.2f",mean));
                System.out.println("mean all node value " + String.format("%.2f", meanX));
                System.out.println("sd " + String.format("%.2f", sd ));


            }else{
                //check of how many times each argument appeared, and their insertion orders (entries kept in node's arraylist)
                for(int i = 1; i < arguments.length; i++){

                    int data = Integer.parseInt(arguments[i]);

                    if(randQuality.tree.hasValue( data )) {
                        int totalAppearence = randQuality.tree.getThisNode(data).arrayList.size();
                        System.out.print(data + " appears " + totalAppearence + " times " + " insertion order: ");
                        for(int j = 0; j < totalAppearence; j++){
                            System.out.print(" " + randQuality.tree.getThisNode(data).arrayList.get(j));
                        }
                        System.out.println();

                    }else{
                        System.out.println(data + " appeared " + 0 + " times ");
                    }
                }
            }




            //TEST ###########

//            System.out.println("the total value of nodes (including duplicates) is : " + randQuality.sumAll);
//            System.out.println("mean all values: " + randQuality.sumAll / randQuality.entry);
//            System.out.println("the standard deviation of this tree is : " + randQuality.calcSD());
//
//            System.out.println("the sum of all distinct nodes is " + randQuality.sum);
//            System.out.println("total number of distinct nodes is " + randQuality.totalNodes);
//            System.out.println("mean node values: " + randQuality.sum / randQuality.totalNodes);


        }


    }


    public class Node {
        //fields
        private int value;
        Node left, right;

        /*
        arraylist with index representing the stamp of when the entry is inserted
        size = total number of occurence
         */
        ArrayList<Integer> arrayList;

        //constructor
        public Node(int value) {
            this.value = value;
            arrayList = new ArrayList<>();
        }

        public int getValue() {
            return this.value;
        }

        //return the node of the input value
        public Node getNode(int value){
            //if value equals node's value then
            //BST contain a node of this value
            if (value == getValue()) {
                return this;
            }

            //if value is less than node's value and left is null
            //return null
            if (value < getValue() && this.left == null) {
                return null;


                //if value is greater than node's value and right is null
                //return false
            } else if (value > getValue() && this.right == null) {
                return null;
            }


            /*
            if value is less than node's value and left is NOT null
            call this method from the node's left child

            similarly, do the same thing for right

             */
            if (this.left != null && value < getValue()) {
                return this.left.getNode(value);
            } else if (this.right != null && value > getValue()) {
                return this.right.getNode(value);
            }

            return null;
        }



        public void insert(int value, int entry) {

        /*
        if the value to be inserted is less than node's value and node.left is null
        create a new node and set node.left to that new node
        remember to return after inserting, or else program will move to the line below

        AND ADD the entry to the array list of the inserted node
         */
            if (value < getValue() && this.left == null) {
                Node node = new Node(value);
                this.left = node;
                node.arrayList.add(entry);
                return;
            }

            if (value > getValue() && this.right == null) {
                Node node = new Node(value);
                this.right = node;
                node.arrayList.add(entry);
                return;
            }

        /*
        if node.left is not null and the value to be inserted is less than the value of the current node
        recursively call this.left.insert(value)
         */
            if (this.left != null && value < getValue()) {
                this.left.insert(value,entry);
            }

        /*
        if node.right is not null and the value to be inserted is greater than the value of the current node
        recursively call this.right.insert(value)
         */
            if (this.right != null && value > getValue()) {
                this.right.insert(value,entry);
            }


        }



        public void insert(int value) {

        /*
        if the value to be inserted is less than node's value and node.left is null
        create a new node and set node.left to that new node
        remember to return after inserting, or else program will move to the line below
         */
            if (value < getValue() && this.left == null) {
                Node node = new Node(value);
                this.left = node;
                return;
            }

            if (value > getValue() && this.right == null) {
                Node node = new Node(value);
                this.right = node;
                return;
            }

        /*
        if node.left is not null and the value to be inserted is less than the value of the current node
        recursively call this.left.insert(value)
         */
            if (this.left != null && value < getValue()) {
                this.left.insert(value);
            }

        /*
        if node.right is not null and the value to be inserted is greater than the value of the current node
        recursively call this.right.insert(value)
         */
            if (this.right != null && value > getValue()) {
                this.right.insert(value);
            }


        }
        /*
        check and see if BST contain a node of this value
         */
        public boolean contain(int value) {
            //if value equals node's value then
            //BST contain a node of this value
            if (value == getValue()) {
                return true;
            }

            //if value is less than node's value and left is null
            //return false
            if (value < getValue() && this.left == null) {
                return false;


                //if value is greater than node's value and right is null
                //return false
            } else if (value > getValue() && this.right == null) {
                return false;
            }


            /*
            if value is less than node's value and left is NOT null
            call this method from the node's left child

            similarly, do the same thing for right

             */
            if (this.left != null && value < getValue()) {
                return this.left.contain(value);
            } else if (this.right != null && value > getValue()) {
                return this.right.contain(value);
            }

            return false;
        }




    }


        public class BST {
            //fields

            private Node overallRoot;

            public BST() {
                overallRoot = null;
            }

            public Node getRoot() {
                return overallRoot;
            }



    /*
    The function insert which  calls  the insert function  on  the  root  of  the  tree,
    and  the function geRoot, which is the public getter function invoked when the printInOrder,printPostOrder,
     and printPreOrder functions are invoked in main */


            public void insert(int value) {
                //if root is not null, call insert on overroot

                if (getRoot() == null) {
                    Node root = new Node(value);
                    overallRoot = root;
                    return;
                }
                overallRoot.insert(value);

            }

//            //insert method for initial entry
//            public void insert(int value, int entry) {
//                //if root is not null, call insert on overroot
//
//                if (getRoot() == null) {
//                    Node root = new Node(value);
//                    overallRoot = root;
//                    return;
//                }
//                overallRoot.insert(value,entry);
//
//            }


            public Node getThisNode(int value){
                if(getRoot() ==null ){
                    return null;
                }

                return overallRoot.getNode(value);
            }

            public boolean hasValue(int value) {
                if (getRoot() == null) {
                    return false;
                }

                return overallRoot.contain(value);
            }

//            public void printInOrder(Node node) {
//                if (node == null) {
//                    return;
//                }
//                printInOrder(node.left);
//                System.out.print(node.getValue() + " ");
//                printInOrder(node.right);
//
//            }

            //InOrder Traversal, add all node's values
            public void InOrder(Node node) {

                if (node == null) {
                    return;
                }
                InOrder(node.left);
                sum = sum + node.getValue();
                InOrder(node.right);

            }


            public void InOrderStandardDeviation(Node node, double meanX) {


                if (node == null) {
                    return;
                }
                InOrderStandardDeviation(node.left, meanX);


                sigma = sigma + Math.pow((node.value - meanX), 2);
                if(!node.arrayList.isEmpty()){
                    for(int i = 1; i < node.arrayList.size(); i++){
                        if(node.arrayList.get(i) !=  null) {
                            sigma =  sigma + Math.pow((node.value - meanX), 2);
                        }else{
                            sigma = sigma + 0;
                        }
                    }

                }

                InOrderStandardDeviation(node.right, meanX);

            }





            public void InOrderTotal(Node node) {
                int result = 0;

                if (node == null) {
                    return;
                }
                InOrderTotal(node.left);



                if(!node.arrayList.isEmpty()){
                    sumAll = sumAll + node.value;
                    for(int i = 1; i < node.arrayList.size(); i++){
                        if(node.arrayList.get(i)!=  null) {
                            sumAll =  sumAll + node.value;
                        }else{
                            sumAll = sumAll + 0;
                        }
                    }

                }
                //result = result + add;


                InOrderTotal(node.right);


            }



            //count total distinct nodes
            public void countNodes(Node node) {

                if (node == null) {
                    return;
                }
                countNodes(node.left);
                if (node != null) {
                    totalNodes++;
                }
                countNodes(node.right);


            }

//            public void printPreOrder(Node node) {
//                if (node == null) {
//                    return;
//                }
//                System.out.print(node.getValue() + " ");
//                printPreOrder(node.left);
//                printPreOrder(node.right);
//
//            }
//
//
//            public void printPostOrder(Node node) {
//                if (node == null) {
//                    return;
//                }
//                printPostOrder(node.left);
//                printPostOrder(node.right);
//                System.out.print(node.getValue() + " ");
//
//            }
//
//
        }
    }





