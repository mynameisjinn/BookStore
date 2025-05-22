package org.example.bookstore.service;

import org.example.bookstore.repository.BookRepository;
import org.example.bookstore.vo.AuthorVO;
import org.example.bookstore.vo.BookVO;
import org.example.bookstore.vo.CategoryVO;
import org.example.bookstore.vo.PublisherVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Value("${file.book.cover.upload.dir}")
    private String uploadDir;

    @Value("${file.book.cover.upload.default}")
    private String defaultDir;

    public List<CategoryVO> selectCategory(){
        return bookRepository.selectCategory();
    }

    public List<AuthorVO> selectAuthor(String searchVal) { return bookRepository.selectAuthor(searchVal); }
    public List<AuthorVO> selectAuthorAll() { return bookRepository.selectAuthorAll(); }

    public List<PublisherVO> selectPublisher(String searchVal) { return bookRepository.selectPublisher(searchVal); }

    public void saveBook(BookVO vo, MultipartFile imgFile) throws IOException {

        if (imgFile != null && !imgFile.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + imgFile.getOriginalFilename();
            Path savePath = Paths.get(uploadDir + fileName);
            Files.createDirectories(savePath.getParent());
            imgFile.transferTo(savePath.toFile());

            // 절대 경로를 상대 경로로 변환
            String fullPath = savePath.toString().replace("\\", "/"); // 윈도우 경로 슬래시 정리
            int idx = fullPath.indexOf("/public");
            String relativePath = (idx != -1) ? fullPath.substring(idx + "/public".length()) : fullPath;

            vo.setImgPath(relativePath);


            // 가격 한국 원화로 저장
//            NumberFormat koreanMoney = NumberFormat.getInstance(Locale.KOREA);
//            vo.setPrice(koreanMoney.format(vo.getPrice()));

        } else {
            vo.setImgPath(defaultDir); // 기본 이미지 처리 ( 실제 해당 경로에 이미지 존재할 것 ! )
        }

        bookRepository.saveBook(vo);
    }

    public List<BookVO> selectBookList() {
        return bookRepository.selectBookList();
    }

    public BookVO selectBookByBookId(int bookId){ return bookRepository.selectBookByBookId(bookId); }

    public void updateBook(BookVO vo, MultipartFile imgFile) throws IOException {

        if (imgFile != null && !imgFile.isEmpty()) {

            String fileName = UUID.randomUUID() + "_" + imgFile.getOriginalFilename();
            Path savePath = Paths.get(uploadDir + fileName);
            Files.createDirectories(savePath.getParent());
            imgFile.transferTo(savePath.toFile());

            // 절대 경로를 상대 경로로 변환
            String fullPath = savePath.toString().replace("\\", "/"); // 윈도우 경로 슬래시 정리
            int idx = fullPath.indexOf("/public");
            String relativePath = (idx != -1) ? fullPath.substring(idx + "/public".length()) : fullPath;

            vo.setImgPath(relativePath);

        } else {
            if(vo.getImgPath() == null) vo.setImgPath(defaultDir);
        }

        bookRepository.updateBook(vo);
    }

    public List<BookVO> searchBook(String searchVal) {

        return bookRepository.selectBookListBySearchVar(searchVal);
    }

    public void deleteBookOne(int bookId) {
        bookRepository.deleteBookOne(bookId);
    }

    public void deleteBookList(List<Integer> bookIdList) {
        bookRepository.deleteBookList(bookIdList);
    }
}
