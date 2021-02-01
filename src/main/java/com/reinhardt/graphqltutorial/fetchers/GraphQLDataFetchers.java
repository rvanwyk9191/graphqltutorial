package com.reinhardt.graphqltutorial.fetchers;

import com.google.common.collect.ImmutableMap;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GraphQLDataFetchers {

    private static List<Map<String, String>> books = Arrays.asList(
            ImmutableMap.of("id", "book-1",
                    "name", "Harry Potter and the Philosopher's Stone",
                    "pageCount", "223",
                    "authorId", "author-1"),
            ImmutableMap.of("id", "book-2",
                    "name", "Moby Dick",
                    "pageCount", "635",
                    "authorId", "author-2"),
            ImmutableMap.of("id", "book-3",
                    "name", "Interview with the vampire",
                    "pageCount", "371",
                    "authorId", "author-3")
    );

    private static List<Map<String, String>> authors = Arrays.asList(
            ImmutableMap.of("id", "author-1",
                    "firstName", "Joanne",
                    "lastName", "Rowling",
                    "addressId", "address-1"),
            ImmutableMap.of("id", "author-2",
                    "firstName", "Herman",
                    "lastName", "Melville",
                    "addressId", "address-2"),
            ImmutableMap.of("id", "author-3",
                    "firstName", "Anne",
                    "lastName", "Rice",
                    "addressId", "address-3")
    );

    private static List<Map<String, String>> addresses = Arrays.asList(
            ImmutableMap.of("id", "address-1",
                    "street", "819 S State St",
                    "city", "Chicago",
                    "state", "Illinois"),
            ImmutableMap.of("id", "address-2",
                    "street", "2780 Fitness Dr",
                    "city", "Naperville",
                    "state", "Illinois"),
            ImmutableMap.of("id", "address-3",
                    "street", "289 N Weber Rd",
                    "city", "Bolingbrook",
                    "state", "Illinois")
    );

    public DataFetcher getBookByIdDataFetcher(){
        return dataFetchingEnvironment -> {
            String bookId = dataFetchingEnvironment.getArgument("id");
            return books
                    .stream()
                    .filter(book -> book.get("id").equals(bookId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher getAuthorDataFetcher(){
        return dataFetchingEnvironment -> {
            Map<String,String> book = dataFetchingEnvironment.getSource();
            String authorId = book.get("authorId");
            return authors
                    .stream()
                    .filter(author -> author.get("id").equals(authorId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher getAuthor(){
        return dataFetchingEnvironment -> {
            String authorId = dataFetchingEnvironment.getArgument("authorId");
            return authors
                    .stream()
                    .filter(author -> author.get("id").equals(authorId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher getAddressByAuthorId(){
        return dataFetchingEnvironment -> {
            Map<String, String> author = dataFetchingEnvironment.getSource();
            String addressId = author.get("addressId");
            return addresses
                    .stream()
                    .filter(address -> address.get("id").equals(addressId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher getBooks(){
        return dataFetchingEnvironment -> {
            int count = dataFetchingEnvironment.getArgument("count");
            return books
                    .stream()
                    .limit(count).collect(Collectors.toList());
        };
    }

}
