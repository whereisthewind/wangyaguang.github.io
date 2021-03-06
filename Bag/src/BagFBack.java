import java.util.Arrays;  
public class BagFBack {  
    private MyElement[] myelements; // 封装的物品  
    private float s; // 背包容量  
    private float nowWeight = 0; // 记录当前以拿重量  
    private float nowPrice = 0; // 记录当前以拿价格  
    private float betterValue; // 记录最多的价格  
  
    /* 
     * 构造方法，用于初始化各个变量 
     */  
    public BagFBack(float[] w, float[] v, float s) {  
        myelements = new MyElement[w.length];  
        for (int i = 0; i < w.length; i++) {  
            myelements[i] = new MyElement();  
            myelements[i].v = v[i];  
            myelements[i].w = w[i];   
        }  
        this.s = s;  
        // 对数组进行价值排序,系统的是从小到大的，但我讲MyElement改了，使得是从大到小  
        Arrays.sort(myelements);  
        System.out.println("物品价值" + "   " + "物品重量");  
        for (int i = 0; i < myelements.length; i++) {  
            System.out.print(myelements[i].v + "    " + myelements[i].w);  
            System.out.println();   
        }  
    }  
    public void traceBack(int t) {  
        if (t >= myelements.length) {  
            // 已经遍历到最下一层，也就是最后一个  
            System.out.println("找到方法");  
            betterValue = nowPrice;  
            System.out.println("最终拿到： " + betterValue);  
            output(myelements);  
            return;  
        }  
        // 首先进入走左子树  
        if (nowWeight + myelements[t].w < s) {  
            // 进入左子树  
            nowWeight += myelements[t].w;  
            nowPrice += myelements[t].v;  
            myelements[t].take = true;  
            traceBack(t + 1);  
            // 还原现场  
            nowWeight -= myelements[t].w;  
            nowPrice -= myelements[t].v;  
            myelements[t].take = false;  
        }  
        // 进入右子树，以及要进入的条件  
        if (bound(t + 1) > betterValue) {  
            traceBack(t + 1);  
        }  
  
    }  
  
    // 输出方法，用于输出  
    public void output(MyElement[] myelements2) {  
        System.out.print("拿重量为这些的物品：");  
        for (int i = 0; i < myelements2.length; i++) {  
            if (myelements2[i].take) {  
                System.out.print(myelements2[i].w + "   ");  
            }  
  
        }  
    }  
  
    /** 
     * 用于计算右边的，如果右边大些，就直接进入 
     */  
    public float bound(int i) {  
        // 计算上界  
        float cleft = s - nowWeight;  
        float bound = nowPrice;  
        // 以物品单位价值递减顺序装入物品  
        while (i < myelements.length && cleft > myelements[i].v) {  
            cleft -= myelements[i].w;  
            bound += myelements[i].v;      
            myelements[i].take = true;
            i++;  
        }  
  
        // // 如果最后一个不能整个放下去,那就装满背包，此问题讨论01背包，要么装，要么不装，所以不用加这一句  
        // if (i < myelements.length) {  
        // bound += (myelements[i].v / myelements[i].w) * cleft;  
        // }  
        return bound;  
    }  
  
    /** 
     * 封装为物品的类 
     */  
    class MyElement implements Comparable {  
        float w;  
        float v;  
        boolean take = false;  
  
        // 更改实现的方法，方便调用系统函数  
        @Override  
        public int compareTo(Object o) {  
            if (v / w < ((MyElement) o).v / ((MyElement) o).w) {  
                return 1; // 注意，此处主要用于排序，从大到小排序，所以故意反  
            } else {  
                return -1;  
            }  
        }  
    }  
  
    public static void main(String[] args) {  
  
        float[] w = { 12,2,3,5 };  
        float[] v = { 19,42,28,12 };  
        float s = 88;  
        BagFBack bagFBack = new BagFBack(w, v, s);  
        // 从第0层开始回溯  
        bagFBack.traceBack(0);
    }  
  
}  
