package vwg.skoda.prcek.objects;

public class VysledekExpSAgregaci {
	
		String mt;
		String pr;
		Long cetnost;
		Long pocZak;
		String pozn;
		long por;
		long porCelk;
		String mbt;

		
		
		public VysledekExpSAgregaci() {
			super();
		}

		public VysledekExpSAgregaci(String pr, Long cetnost, Long pocZak) {
			super();
			this.pr = pr;
			this.cetnost = cetnost;
			this.pocZak = pocZak;
		}
		
		public String getMt() {
			return mt;
		}
		public void setMt(String mt) {
			this.mt = mt;
		}
		public String getPr() {
			return pr;
		}
		public void setPr(String pr) {
			this.pr = pr;
		}
		public Long getCetnost() {
			return cetnost;
		}
		public void setCetnost(Long cetnost) {
			this.cetnost = cetnost;
		}
		public Long getPocZak() {
			return pocZak;
		}
		public void setPocZak(Long pocZak) {
			this.pocZak = pocZak;
		}
		public String getPozn() {
			return pozn;
		}
		public void setPozn(String pozn) {
			this.pozn = pozn;
		}
		public long getPor() {
			return por;
		}
		public void setPor(long por) {
			this.por = por;
		}
		public long getPorCelk() {
			return porCelk;
		}
		public void setPorCelk(long porCelk) {
			this.porCelk = porCelk;
		}
		public String getMbt() {
			return mbt;
		}
		public void setMbt(String mbt) {
			this.mbt = mbt;
		}

		

}
