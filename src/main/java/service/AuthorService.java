package service;

import dao.AuthorDAO;
import dto.AuthorDTO;
import entity.Author;
import mappers.AuthorReadMapper;
import mappers.AuthorWriteUpdateMapper;


public class AuthorService {

    private AuthorDAO authorDAO = new AuthorDAO();
    private final AuthorReadMapper authorReadMapper = new AuthorReadMapper();
    private final AuthorWriteUpdateMapper authorWriteUpdateMapper = new AuthorWriteUpdateMapper();

    public Author findByNameOrElseAdd(String name) {
        Author author = authorDAO.findByName(name);
        if (author == null) {
            return authorDAO.add(name);
        }
        return author;
    }

    public Author findById(Integer id) {
        return authorDAO.getById(id);
    }

    public Author add(Author author) {
        return authorDAO.add(author);
    }

    public Author add(AuthorDTO authorDTO) {
        return authorDAO.add(authorWriteUpdateMapper.map(authorDTO));
    }

    public AuthorDTO getAuthorDTO(Author author) {
        if (author == null) {
            return null;
        }
        return authorReadMapper.map(author);
    }

    public Author getAuthor(AuthorDTO authorDTO) {
        if (authorDTO == null) {
            return null;
        }
        return authorWriteUpdateMapper.map(authorDTO);
    }

    public boolean update(Integer id, AuthorDTO authorDTO) {
        Author author = authorDAO.getById(id);
        if (author == null) {
            return false;
        }
        Author updateFrom = authorWriteUpdateMapper.map(authorDTO);
        author.setFirstname(updateFrom.getFirstname());
        author.setLastname(updateFrom.getLastname());
        authorDAO.update(author);
        return true;
    }

    public boolean delete(Integer id) {
        return authorDAO.delete(id);
    }
}
