package vwg.skoda.prcek.objects;

public class AjaxClass {

	static public class TestDbPrcekAjax {

		private String prcekDate;
		private long prcekLatence;

		public TestDbPrcekAjax() {
		}

		public String getPrcekDate() {
			return prcekDate;
		}

		public void setPrcekDate(String date) {
			this.prcekDate = date;
		}

		public long getPrcekLatence() {
			return prcekLatence;
		}

		public void setPrcekLatence(long prcekLatence) {
			this.prcekLatence = prcekLatence;
		}

	}

	static public class TestDbMbtAjax {

		private String mbtDate;
		private long mbtLatence;

		public TestDbMbtAjax() {
		}

		public String getMbtDate() {
			return mbtDate;
		}

		public void setMbtDate(String date) {
			this.mbtDate = date;
		}

		public long getMbtLatence() {
			return mbtLatence;
		}

		public void setMbtLatence(long mbtLatence) {
			this.mbtLatence = mbtLatence;
		}

	}

	static public class TestDbKomunikaceAjax {
		
		private String komunikaceDate;
		private long komunikaceLatence;

		public TestDbKomunikaceAjax() {
		}

		public String getKomunikaceDate() {
			return komunikaceDate;
		}

		public void setKomunikaceDate(String date) {
			this.komunikaceDate = date;
		}

		public long getKomunikaceLatence() {
			return komunikaceLatence;
		}

		public void setKomunikaceLatence(long komunikaceLatence) {
			this.komunikaceLatence = komunikaceLatence;
		}

	}

}
