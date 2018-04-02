import java.util.*;

class Rod_Cutting_Project2{
	int mi=0;
	static class Rod{
		int size=0;
		int price=0;
		int maxNo=0;
		int minNo=0;
		int penalty=0;
		int maxPenalty=0;
		int num=0;
		
		Rod(int num, int size,int price,int minNo,int maxNo, int penalty,int maxPenalty){
			this.num=num;
			this.size=size;
			this.price=price;
			this.maxNo=maxNo;
			this.minNo=minNo;
			this.penalty=penalty;
			this.maxPenalty=maxPenalty;
		}
	}
	int select(Rod[] r,int k,int f){
		int[][] C=new int[k+1][f+1];
		for(int i=0;i<k+1;i++)
		{
			for(int j=0;j<f+1;j++){
				C[i][j]=Integer.MIN_VALUE;
			}
		}
		return select1(r,k,f,C);
	}
	
	int select1(Rod[] r,int k,int i,int[][] C){
		if(i==0)
			return 0;
		if(k==0 && i==0)
			return 0;
		if(C[k][i]>=0)
		{	
			return C[k][i];
		}
		int max=Integer.MIN_VALUE;
		
		for(int num=0;num<=r[i].maxNo;num++){
			
			if(num*r[i].size>k)
				break;
			int total=0;
			int penalty=0;
			if(num<r[i].minNo)
				penalty=-maxi((r[i].minNo-num)*r[i].penalty,r[i].maxPenalty);
			total=penalty+num*r[i].price+select1(r,k-r[i].size*num,i-1,C);
			
			//System.out.println(total+" : "+" " +k+" "+i);
			if(max<total)
				max=total;
		}
		C[k][i]=max;
		//System.out.println(C[k][i]);
		return max;
			
		
	}
	
	int maxi(int a,int b){
		if(a<b)
			return a;
		else
			return b;
	}
	public static void main(String[] args){
		Rod_Cutting_Project2 rcp=new Rod_Cutting_Project2();
		Scanner scan=new Scanner(System.in);
		int n=scan.nextInt();
		Rod[] r=new Rod[n+1];
		for(int i=0;i<=n;i++){
			
			r[i]=new Rod(scan.nextInt(), scan.nextInt(),scan.nextInt(),scan.nextInt(),scan.nextInt(),scan.nextInt(),scan.nextInt());
		}
		
		
			System.out.println(rcp.select(r,scan.nextInt(),r.length-1));
		
	}
}
			
		
