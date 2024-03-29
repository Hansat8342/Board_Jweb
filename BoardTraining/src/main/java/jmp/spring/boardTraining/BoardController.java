package jmp.spring.boardTraining;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jmp.spring.service.BoardService;
import jmp.spring.vo.BoardVo;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class BoardController {
	
	@Autowired
	BoardService service;
	
	@GetMapping("/board/list") 
	public void getList(Model model) { //반횐되는게 없으면 저장된 주소를 반환
		
		model.addAttribute("list", service.getList());
		log.info("getList()===");
	}
	
	// 등록페이지
	@GetMapping("/board/register")
	public void Insert() {
		log.info("=========insert");
	}
	
	// 등록한 게시글이 확인을 받고 등록되는 프로세스
	@PostMapping("board/register")
	public String insertExe(BoardVo vo, RedirectAttributes rttr) { //리다이렉트 는 모델과 비슷한 역할
		log.info("==========="+vo); //vo 가 들어왔나 출력
		int res = service.insertBoard(vo); //vo를 삽입
		log.info("=========="+vo);
		
		rttr.addFlashAttribute("resMsg", vo.getBno()+"번 게시글이 등록 되었습니다.");
		return "redirect:/board/list";
	}
	
	@GetMapping({"/board/get", "/board/edit"})
	public void boardGet(BoardVo vo, Model model) {
		//상세정보 조회 , 모델에 담아서 화면에 전달
		model.addAttribute("vo",service.get(vo.getBno()));
	}
	
	@PostMapping("/board/edit")
	public String editEXe(BoardVo vo, RedirectAttributes rttr) {
		
		int res = service.update(vo);
		String resMsg="";
		
		if(res>0) {
			resMsg="수정 되었습니다.";
		}else {
			resMsg="수정 작업에 실패 했습니다. 관리자에게 문의해 주세요.";
		}
		
		rttr.addAttribute("bno",vo.getBno()); // 상세보기를 위한 bno 파라메터 처리
		rttr.addFlashAttribute("resMsg",resMsg);
		return "redirect:/board/get";
	}
	
	@GetMapping("/board/delete")
	public String delete(BoardVo vo, RedirectAttributes rttr) {
		
		//삭제처리
		int res = service.delete(vo.getBno());
		String resMsg = "";
		//삭제 성공 -> 리스트
		if(res>0) {
			resMsg=vo.getBno()+"번 게시글이 삭제 되었습니다.";
			rttr.addFlashAttribute("resMsg",resMsg);
			return "redirect:/board/list";			
		}else {
			//삭제 실패 -> 상세화면
			resMsg="게시글 삭제 처리에 실패 했습니다.";
			rttr.addFlashAttribute("resMsg",resMsg);
			rttr.addAttribute("bno",vo.getBno());
			return "redirect:/board/get";
		}
		
	}
}
