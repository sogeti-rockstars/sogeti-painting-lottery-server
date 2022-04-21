package com.sogetirockstars.sogetipaintinglotteryserver.business.contestant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Contestant
 */
@Entity
@Table
public class Contestant {
    @Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
    Long id;
    String name;
    String address;

    public Contestant(){}
    public Contestant(Long id, String name, String address) { this.id=id; this.name = name; this.address = address; }
    public Contestant(String name, String address) { this.name = name; this.address = address; }

    public Long   getId()                  { return id;      }
    public String getName()                { return name;    }
    public String getAddress()             { return address; }

    public void setName(String name)       { this.name    = name;    }
    public void setId(Long id)             { this.id      = id;      }
    public void setAddress(String address) { this.address = address; }

}
