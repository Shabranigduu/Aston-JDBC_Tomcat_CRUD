package service;

import dao.AuthorDAO;
import dao.PublisherDAO;
import dto.AuthorDTO;
import entity.Author;
import mappers.AuthorReadMapper;
import mappers.AuthorWriteUpdateMapper;

import java.util.Optional;

public class AuthorService {

    private static final AuthorService INSTANCE = new AuthorService();

    public static AuthorService getInstance() {
        return INSTANCE;
    }

    private final AuthorReadMapper authorReadMapper = new AuthorReadMapper();
    private final AuthorWriteUpdateMapper authorWriteUpdateMapper = new AuthorWriteUpdateMapper();

    public Author findByName(String name) {
        return AuthorDAO.getInstance().findByName(name);
    }
    public Author findByNameOrElseAdd(String name) {
        Author author = AuthorDAO.getInstance().findByName(name);
        if(author==null){
            return AuthorDAO.getInstance().add(name);
        }
        return author;
    }

    public Author findById(Integer id) {
        return AuthorDAO.getInstance().getById(id);
    }

    public Author add(Author author){
        return AuthorDAO.getInstance().add(author);
    }

    public Author add(AuthorDTO authorDTO){
        return AuthorDAO.getInstance().add(getAuthor(authorDTO));
    }

    public AuthorDTO getAuthorDTO(Author author){
        if(author==null){
            return null;
        }
        return authorReadMapper.map(author);
    }

    public Author getAuthor(AuthorDTO authorDTO){
        if(authorDTO==null){
            return null;
        }
        return authorWriteUpdateMapper.map(authorDTO);
    }
}
