package com.gustavosdaniel.backend.exception;

import com.gustavosdaniel.backend.category.CategoryNotFoundException;
import com.gustavosdaniel.backend.category.ExceptionCategoryNameExists;
import com.gustavosdaniel.backend.image.ExceptionErrorUploadImage;
import com.gustavosdaniel.backend.product.ExceptionProductNameExists;
import com.gustavosdaniel.backend.product.ProductIdNotFoundException;
import com.gustavosdaniel.backend.product.ProductNameAlreadyExistsException;
import com.gustavosdaniel.backend.product.ProductNameNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {

        log.warn("Validation failed {}", exception.getMessage());

        Map<String, String> fieldErrors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach((fieldError) -> {
            fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        ErrorResponse errorResponse = new ErrorResponse("Validation failed",
                "Erro de validação nos Campos",
                LocalDateTime.now(),
                fieldErrors);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ExceptionCategoryNameExists.class)
    public ResponseEntity<ErrorResponse> handleExceptionCategoryNameExists(
            ExceptionCategoryNameExists exception
    ){
        ErrorResponse errorResponse = new ErrorResponse("Nome da Categoria Já existe",
                "O nome da categoria informado já está sendo utilizado",
                LocalDateTime.now(),
                null);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(ExceptionErrorUploadImage.class)
    public ResponseEntity<ErrorResponse> handleExceptionErrorUploadImage(
            ExceptionErrorUploadImage exception
    ){
        ErrorResponse errorResponse = new ErrorResponse("Erro ao carregar imagem",
                exception.getMessage(),
                LocalDateTime.now(),
                null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFoundException(
            CategoryNotFoundException  exception) {
        ErrorResponse errorResponse = new ErrorResponse("Categoria não encontrada",
                "Não foi possivel encontrar uma categoria",
                LocalDateTime.now(),
                null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ExceptionProductNameExists.class)
    public ResponseEntity<ErrorResponse> handleExceptionProductNameExists(
            ExceptionProductNameExists exception) {
        ErrorResponse errorResponse = new ErrorResponse("Nome do produto já existe",
                "O nome do Produto informado já está sendo utilizado",
                LocalDateTime.now(),
                null);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(ProductIdNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductIdNotFoundException(
            ProductIdNotFoundException  exception) {
        ErrorResponse errorResponse = new ErrorResponse("Produto com ID não encontrado",
                "Produto com o ID inserido não encontrado",
                LocalDateTime.now(),
                null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ProductNameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNameNotFoundException(
            ProductNameNotFoundException exception
    ){
        ErrorResponse errorResponse = new ErrorResponse("Nome do Produto não encontrado",
                "Nome do produto pesquisado não encontrado",
                LocalDateTime.now(),
                null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ProductNameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleProductNameAlreadyExistsException(
            ProductNameAlreadyExistsException  exception){

        ErrorResponse errorResponse = new ErrorResponse(
                "Nome do Produto Já Utilizado",
                "O nome do produto que voce tentou atualizar já está sendo utilizado",
                LocalDateTime.now(),
                null
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
}
