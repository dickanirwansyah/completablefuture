package com.rnd.democompletetablefuture.service;

import com.rnd.democompletetablefuture.entity.Users;
import com.rnd.democompletetablefuture.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Async
    public CompletableFuture<List<Users>> saveUsers(MultipartFile file) throws Exception{
        long start=System.currentTimeMillis();
        List<Users> usersList = parseCsv(file);
        log.info("saving list of users of size {} ",usersList.size(), " - "+Thread.currentThread().getName());
        userRepository.saveAll(usersList);
        long end=System.currentTimeMillis();
        log.info("Total time={}",(start - end));
        return CompletableFuture.completedFuture(usersList);
    }

    @Async
    public CompletableFuture<List<Users>> findAllUsers(){
        log.info("get list of users by thread - "+Thread.currentThread().getName());
        List<Users> usersList = userRepository.findAll();
        return CompletableFuture.completedFuture(usersList);
    }

    private List<Users> parseCsv(final MultipartFile file) throws Exception{
        final List<Users> users = new ArrayList<>();
        try{
            try(final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()))){
                String line;
                while ((line = bufferedReader.readLine()) != null){
                    final String[] data = line.split(",");
                    final Users dataUsers = new Users();
                    dataUsers.setName(data[0]);
                    dataUsers.setEmail(data[1]);
                    dataUsers.setGender(data[2]);
                    users.add(dataUsers);
                }
                return users;
            }
        }catch (final Exception e){
            log.error("failed to parse CSV file {}",e.getMessage());
            throw new Exception("failed to parse CSV file {} ",e);
        }
    }
}
