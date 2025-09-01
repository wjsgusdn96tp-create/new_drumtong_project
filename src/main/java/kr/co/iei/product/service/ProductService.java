package kr.co.iei.product.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.product.dao.ProductDao;
import kr.co.iei.product.vo.Product;
import kr.co.iei.product.vo.productListDate;

@Service
public class ProductService {
	@Autowired
	private ProductDao productDao;
	

	public productListDate productListDate(int reqPage) {
		/*한페이지당 출력 리스트 갯수 설정 완료*/
		int numPerPage =6;
		int end = reqPage*numPerPage;
		int start = end-numPerPage+1;
		HashMap<String,Object> param = new HashMap<String, Object>();
		param.put("start", start);
		param.put("end", end);
		
		/*반복문 종료 시점을 위한 전체 상품 수 카운트*/
		int totalCount = productDao.selectProductTotalCount();
		/*페이지 수 계산(올림계산)*/
		int totalPage = (int)Math.ceil(totalCount/(double)numPerPage);
		/*페이지 네비 사이즈 설정*/
		int pageNaviSize = 3;
		/*페이지 네비 시작번호 지정  -> 컴퓨터상 나머지=/ , 몫=% */
		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize+1;
		
		/*페이지 네비 html생성*/
		String pageNavi = "<ul class='pagination circle-style'>";
		if(pageNo != 1) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/notice/list?reqPage="+(pageNo-1)+"'>";
			pageNavi += "<span class='material-icons'> chevron_left </span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		
		/*이해 요망*/
		for(int i=0; i<pageNaviSize; i++) {
			pageNavi += "<li>";
			if(pageNo == reqPage) {
				pageNavi += "<a class='page-item active-page' href='/notice/list?reqPage="+pageNo+"'>";
			} else {
				pageNavi += "<a class='page-item' href='/notice/list?reqPage="+pageNo+"'>";				
			}
			pageNavi += pageNo;
			pageNavi += "</a>";
			pageNavi += "</li>";
			
			pageNo++;
			// 페이지를 제작하다가 마지막 페이지를 출력했으면 더이상 반복하지 않고 반복문 종료
			if(pageNo > totalPage) {
				break;
			}
		}
		// 다음버튼(최종 페이지를 출력하지 않은 경우)
		if(pageNo <= totalPage) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/notice/list?reqPage="+pageNo+"'>";
			pageNavi += "<span class='material-icons'> chevron_right </span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		pageNavi += "</ul>"; 
		
		//System.out.println(pageNavi);
		
		List list = productDao.selectProductList(param);
		
		productListDate pld= new productListDate(list, pageNavi);
		
		return pld;
	}
	
	
	@Transactional
	public int productInsertDrink(Product p, String productBestTbl) {
		/*int referencesProductNo =0;*/
		if(productBestTbl.equals("productBest")) {
			/*referencesProductNo ++;*/
			int result = productDao.productInsertDrink(p);
			if(result > 0) {
				/*p.setProductNo(referencesProductNo);*/
				String productName= p.getProductName();
				Product productSearch = productDao.productInsertSearch(productName);
				int resultBest = productDao.productBestTblInsert(productSearch);
				if(resultBest > 0) {
					return resultBest;/*1*/
				}else {
					int productInsertCancel = productDao.productInsertDelete(p);
					if(productInsertCancel >0) {
						return resultBest;/*0*/
					}else {
						return 10;/*일반상품등록, 대표상품 등록실패*/
					}
				}
			}else{
				return result;
			}
		}else {
			/*referencesProductNo ++;*/
			int result = productDao.productInsertDrink(p);
			return result;/*1*/
		}
	}

	@Transactional
	public int productDelete(int productNo) {
		Product productBest = productDao.productBestSelect(productNo);
		if(productBest != null) {
			int resultBestD = productDao.productBestDelete(productNo);
			if(resultBestD > 0) {
				int resultD = productDao.productDelete(productNo);
				if(resultD > 0) {
					return resultD;/*1*/
				}else {
					return 10;/*대표상품만 삭제*/
				}
			}
		}else{
			int resultD = productDao.productDelete(productNo);
			if(resultD > 0) {
				return resultD;/*1*/				
			}
		}return 0;/*삭제 실패*/
		
	}
}
