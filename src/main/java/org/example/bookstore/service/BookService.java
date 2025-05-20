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
import java.util.List;
import java.util.UUID;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Value("${file.upload.dir}")
    private String uploadDir;

    public List<CategoryVO> selectCategory(){
        return bookRepository.selectCategory();
    }

    public List<AuthorVO> selectAuthor(String searchVal) { return bookRepository.selectAuthor(searchVal); }

    public List<PublisherVO> selectPublisher(String searchVal) { return bookRepository.selectPublisher(searchVal); }

    public void saveBook(BookVO vo, MultipartFile imgFile) throws IOException {

        if (imgFile != null && !imgFile.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + imgFile.getOriginalFilename();
            Path savePath = Paths.get(uploadDir + fileName);
            Files.createDirectories(savePath.getParent());
            imgFile.transferTo(savePath.toFile());

            vo.setImgPath(savePath.toString()); // 파일 경로
        } else {
            vo.setImgPath("D:\\project\\2025\\book-store\\frontend\\public\\images\\default-book.jpg"); // 기본 이미지 처리
        }

        bookRepository.saveBook(vo);
    }

    public List<BookVO> selectBookList() {
        return bookRepository.selectBookList();
    }
}
