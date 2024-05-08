package org.example;

import org.example.models.OvoZayavka;
import org.example.repo.OvoZayavkaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OvoService {
    @Autowired
    OvoZayavkaRepository ozr;
    @Transactional
    public void saveZayavka(OvoZayavka zy){
        ozr.save(zy);
    }
}
