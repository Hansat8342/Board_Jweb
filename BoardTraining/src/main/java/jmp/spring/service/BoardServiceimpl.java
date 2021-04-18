package jmp.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jmp.spring.mapper.BoardMapper;
import jmp.spring.vo.BoardVo;

@Service
public class BoardServiceimpl implements BoardService{

	@Autowired // 중요. 오토와이어드 안쓰면 널포인트
	BoardMapper mapper;
	
	@Override
	public List<BoardVo> getList() {
		return mapper.getList();
	}

	@Override
	public int insertBoard(BoardVo vo) {
		return mapper.insertBoard(vo);
	}

	@Override
	public BoardVo get(int bno) {
		return mapper.get(bno);
	}

	@Override
	public int update(BoardVo vo) {
		// TODO Auto-generated method stub
		return mapper.update(vo);
	}

	@Override
	public int delete(int bno) {
		return mapper.delete(bno);
	}

}
