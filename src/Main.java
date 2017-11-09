import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

final class Vertice {
    private int num;
    private int arvore;

    public Vertice(int pNum, int pCont){
        this.num = pNum;
        this.arvore = pCont;
    }

    public int getArvore() {
        return arvore;
    }

    public void setArvore(int arvore) {
        this.arvore = arvore;
    }
}

final class Aresta implements Comparable<Aresta> {

    private int peso;
    private int ordem;
    private int[] a = new int[2];

    Aresta(String[] aux){
        this.a[0] = Integer.parseInt(aux[0]);
        this.a[1] = Integer.parseInt(aux[1]);
        this.peso = Integer.parseInt(aux[2]);
    }

    public int[] getA() {
        return a;
    }

    public void setA(int[] a) {
        this.a = a;
    }

    public int getPeso() {
        return peso;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public void mostrar(){
        System.out.println(String.valueOf(a[0]) + " " + String.valueOf(a[1]) + " " + this.getPeso());
    }

    public int compareTo(Aresta pAresta) {
        if (this.a[0] < pAresta.a[0]){
            return -1;
        } else if (this.a[0] > pAresta.a[0]) {
            return 1;
        } else {
            if (this.a[1] < pAresta.a[1]){
                return -1;
            } else {
                return 1;
            }
        }
    }
}

final class Grafo {

    public List<Vertice> vertices = new ArrayList<Vertice>();
    public List<Aresta> arestas = new ArrayList<Aresta>();

    public List<Vertice> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vertice> vertices) {
        this.vertices = vertices;
    }

    public List<Aresta> getArestas() {
        return arestas;
    }

    public void setArestas(List<Aresta> arestas) {
        this.arestas = arestas;
    }

    public void ordem(){
        for (int i = 0; i < this.arestas.size(); i++){
            this.arestas.get(i).setOrdem(i);
        }
    }

    public Aresta pesoMinimo(){
        int menor = this.arestas.get(0).getPeso();
        for (int i = 0; i < this.arestas.size(); i++){
            if (this.arestas.get(i).getPeso() <= menor){
                menor = this.arestas.get(i).getPeso();
            }
        }
        List<Aresta> menores = new ArrayList<Aresta>();
        for (int i = 0; i < this.arestas.size(); i++){
            if (menor == this.arestas.get(i).getPeso()){
                menores.add(this.arestas.get(i));
            }
        }
        Aresta aux = menores.get(0);
        if (!(menores.size() == 1)){
            for (int i = 0; i < menores.size(); i++){
                if (aux.getOrdem() > menores.get(i).getOrdem()){
                    aux = menores.get(i);
                }
            }
        }
        this.arestas.remove(aux);
        return aux;
    }

    public boolean naoCiclo(Aresta pAresta){
        int[] aux = pAresta.getA();
        if (this.vertices.get(aux[0]-1).getArvore() == this.vertices.get(aux[1]-1).getArvore()){
            return false;
        } else {
            Main.cont++;
            int a = this.vertices.get(aux[0]-1).getArvore();
            int b = this.vertices.get(aux[1]-1).getArvore();
            for (int i = 0; i < this.vertices.size(); i++){
                if ((this.vertices.get(i).getArvore() == a) || (this.vertices.get(i).getArvore() == b)){
                    this.vertices.get(i).setArvore(Main.cont);
                }
            }
            return true;
        }

    }

    public void consertaAresta(List<Aresta> pLista){
        List<Aresta> organizada = new ArrayList<Aresta>();
        for (int i = 0; i < pLista.size(); i++){
            int[] aux = pLista.get(i).getA();
            if (aux[0] > aux[1]){
                int nAux = aux[0];
                aux[0] = aux[1];
                aux[1] = nAux;
                pLista.get(i).setA(aux);
            }
        }
        //return organizada;
    }

    public List<Aresta> kruskal(){
        List<Aresta> mst = new ArrayList<Aresta>();
        while (this.arestas.size() != 0){
            Aresta aux = this.pesoMinimo();
            if (this.naoCiclo(aux)){
                mst.add(aux);
            }
            this.arestas.remove(aux);
        }
        return mst;
    }
}

final class Main{

    static int cont = 0;

    public static void main(String[] args){
        Grafo grafo = new Grafo();

        Scanner in = new Scanner(System.in);

        String[] valores = in.nextLine().split("\\s+");
        int v1 = Integer.parseInt(valores[0]);
        int v2 = Integer.parseInt(valores[1]);

        for (int i = 1; i <= v1; i++){
            cont++;
            grafo.vertices.add(new Vertice(i, Main.cont));
        }

        for (int i = 1; i <=v2; i++){
            String[] aresta = in.nextLine().split("\\s+");
            if (aresta[0] != aresta[1]){
                grafo.arestas.add(new Aresta(aresta));
            }
        }

        grafo.consertaAresta(grafo.getArestas());

        Collections.sort(grafo.getArestas());

        grafo.ordem();

        List<Aresta> mst = grafo.kruskal();

        grafo.consertaAresta(mst);

        Collections.sort(mst);

        for (int i = 0; i < mst.size(); i++){
            Aresta aux = mst.get(i);
            aux.mostrar();
        }
    }
}