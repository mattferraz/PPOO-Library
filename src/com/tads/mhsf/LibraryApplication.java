package com.tads.mhsf;

import com.tads.mhsf.model.*;
import com.tads.mhsf.repository.CustomerRepository;
import com.tads.mhsf.service.BookService;
import com.tads.mhsf.service.CustomerService;
import com.tads.mhsf.service.LibrarianService;
import com.tads.mhsf.service.LoanService;
import com.tads.mhsf.util.MockData;
import com.tads.mhsf.util.TableGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class LibraryApplication {

    public static void main(String[] args) {
        final LoanService loanService = new LoanService();
        final BookService bookService = new BookService();
        final CustomerService customerService = new CustomerService();
        final LibrarianService librarianService = new LibrarianService();

        MockData.mock();
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("=".repeat(30));
            System.out.println("BEM-VINDO À BIBLIOTECA IFPE");
            System.out.println("=".repeat(30));

            System.out.println("""
                    [ 1 ] - Realizar login como cliente
                    [ 2 ] - Realizar login como bibliotecário
                    [ 9 ] - Sair""");

            System.out.print("> ");
            int option = input.nextInt();

            if (option == 9) break;

            System.out.println("=".repeat(30));

            if (option == 2) {
                try {
                    System.out.print("Email: ");
                    String email = input.next();
                    System.out.print("Senha: ");
                    String password = input.next();
                    Librarian librarian = librarianService.findLibrarianByLoginData(email, password);
                    System.out.format("Login realizado com sucesso! Bem-vindo, %s.\n", librarian.getName().split(" ")[0]);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return;
                }

                System.out.println("=".repeat(30));

                while (true) {
                    System.out.println("""
                        O que você deseja fazer?
                        [ 1 ] - Cadastrar livro
                        [ 2 ] - Cadastrar cliente
                        [ 3 ] - Visualizar empréstimos
                        [ 4 ] - Visualizar livros cadastrados
                        [ 5 ] - Visualizar clientes;
                        [ 9 ] - Voltar""");
                    System.out.print("> ");
                    option = input.nextInt();

                    if (option == 9) break;

                    switch (option) {
                        case 1 -> {
                            System.out.print("Título: ");
                            String title = input.next();

                            System.out.print("Autor: ");
                            String author = input.next();

                            System.out.print("Gênero: ");
                            String genre = input.next();

                            System.out.print("Quantidade de Páginas: ");
                            int printLength = input.nextInt();

                            Book book = new Book(title, genre, author, printLength);
                            bookService.createBook(book);

                            System.out.println("Livro cadastrado com sucesso!");
                        }

                        case 2 -> {
                            System.out.print("Nome: ");
                            String name = input.next();

                            System.out.print("Email: ");
                            String email = input.next();

                            System.out.print("Senha: ");
                            String password = input.next();

                            Customer customer = new Customer(name, email, password);
                            customerService.createCustomer(customer);

                            System.out.println("Cliente cadastrado com sucesso!");
                        }

                        case 3 -> {
                            List<Loan> loans = loanService.findAllLoans();
                            if (!loans.isEmpty()) {
                                String[] headers = {"ID", "Livro", "Cliente", "Data do Empréstimo", "Data de Recebimento", "Status"};
                                int[] columnsLength = {6, 40, 25, 20, 20, 20};
                                List<List<String>> rows = new ArrayList<>();
                                for (Loan loan : loans) {
                                    List<String> row = new ArrayList<>();
                                    row.add(loan.getId().toString());
                                    row.add(loan.getBook().getTitle());
                                    row.add(loan.getCustomer().getName());
                                    row.add(loan.getLoanDate().toString());
                                    row.add(loan.getReturnDate().toString());
                                    row.add(loan.getLoanStatus().toString());
                                    rows.add(row);
                                }
                                TableGenerator.generate(headers, rows, columnsLength);

                                System.out.println("""
                                        O que você deseja fazer?
                                        [ 1 ] - Receber livro e finalizar empréstimo
                                        [ 9 ] - Voltar""");
                                System.out.print("> ");
                                option = input.nextInt();

                                if (option == 1) {
                                    System.out.print("ID do empréstimo: ");
                                    Long loanId = input.nextLong();

                                    try {
                                        Loan loan = loanService.findLoanById(loanId);
                                        loan.setLoanStatus(LoanStatus.FINALIZADO);
                                        loan.getBook().setBookStatus(BookStatus.AVAILABLE);
                                        System.out.println("Empréstimo finalizado com sucesso!");
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                            } else {
                                System.out.println("Nenhum empréstimo foi realizado.");
                            }
                        }

                        case 4 -> {
                            List<Book> books = bookService.findAllBooks();
                            displayBooks(books);
                        }

                        default -> System.out.println("Opção inválida. Tente novamente.");
                    }
                }
            }

            else if (option == 1) {
                try {
                    System.out.print("Email: ");
                    String email = input.next();
                    System.out.print("Senha: ");
                    String password = input.next();
                    Customer customer = customerService.findCustomerBySignInData(email, password);
                    System.out.format("Login realizado com sucesso! Bem-vindo, %s.\n", customer.getName().split(" ")[0]);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return;
                }

                while (true) {
                    String notificationAlert = CustomerRepository.customerSignedIn.getNotifications().isEmpty() ? "" : "(!)";
                    System.out.println("=".repeat(30));
                    System.out.printf("""
                        O que você deseja fazer?
                        [ 1 ] - Solicitar empréstimo
                        [ 2 ] - Visualizar livros
                        [ 3 ] - Visualizar lista de desejos
                        [ 4 ] - Notificações %s
                        [ 9 ] - Voltar
                        """, notificationAlert);
                    System.out.print("> ");
                    option = input.nextInt();

                    if (option == 9) break;

                    if (option == 1 || option == 2) {
                        displayBooks(bookService.findAllBooks());
                    }

                    switch (option) {
                        case 1 -> {
                            try {
                                System.out.print("Digite o ID do livro que você deseja emprestado: ");
                                Long id = input.nextLong();
                                Book book = bookService.findBookById(id);
                                Loan loan = new Loan(book, CustomerRepository.customerSignedIn);
                                loanService.createLoan(loan);
                                System.out.println("Empréstimo solicitado com sucesso!");
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }

                        case 2 -> {
                            System.out.println("""
                                    O que você deseja fazer?
                                    [ 1 ] - Adicionar livro à lista de desejos
                                    [ 9 ] - Voltar""");
                            System.out.print("> ");
                            option = input.nextInt();
                            if (option == 1) {
                                try {
                                    System.out.print("ID do livro: ");
                                    Long idBook = input.nextLong();
                                    Book book = bookService.findBookById(idBook);
                                    CustomerRepository.customerSignedIn.getWishList().add(book);
                                    book.addPropertyChangeListener(CustomerRepository.customerSignedIn);
                                    System.out.println(book.getTitle() + " adicionado à sua lista de desejos com sucesso!");
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                        }

                        case 3 -> {
                            System.out.println("Sua lista de desejos:");
                            displayBooks(CustomerRepository.customerSignedIn.getWishList());
                        }

                        case 4 -> {
                            System.out.println("Notificações");
                            List<String> notifications = CustomerRepository.customerSignedIn.getNotifications();
                            if (!notifications.isEmpty()) {
                                for (String notification : notifications) {
                                    System.out.println(notification);
                                }
                            } else {
                                System.out.println("Você não possui nenhuma notificação...");
                            }
                        }
                    }
                }

            }
        }

    }

    private static void displayBooks(List<Book> books) {
        if (!books.isEmpty()) {
            String[] headers = {"ID", "Título", "Autor", "Gênero", "Páginas", "Situação"};
            int[] columnsSize = {5, 40, 20, 20, 10, 15};
            List<List<String>> rows = new ArrayList<>();
            for (Book book : books) {
                List<String> row = new ArrayList<>();
                row.add(book.getId().toString());
                row.add(book.getTitle());
                row.add(book.getAuthor());
                row.add(book.getGenre());
                row.add(book.getPrintLength().toString());
                row.add(book.getBookStatus().toString());
                rows.add(row);
            }
            TableGenerator.generate(headers, rows, columnsSize);
        } else {
            System.out.println("Nenhum livro foi encontrado.");
        }
    }

}
