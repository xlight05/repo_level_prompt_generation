/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld;

/**
 *
 * @author Inma
 */
public class Service {
    private String _name;
    private String _owner;
    
    public Service(){
        _name="name";
        _owner="owner";
    }
    
    public Service(String n,String own){
        this._name=n;
        this._owner=own;
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public String getOwner() {
        return _owner;
    }

    public void setOwner(String _owner) {
        this._owner = _owner;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Service other = (Service) obj;
        if ((this._name == null) ? (other._name != null) : !this._name.equals(other._name)) {
            return false;
        }
        if ((this._owner == null) ? (other._owner != null) : !this._owner.equals(other._owner)) {
            return false;
        }
        return true;
    }


    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + (this._name != null ? this._name.hashCode() : 0);
        hash = 13 * hash + (this._owner != null ? this._owner.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "(Service (NAME " + _name + ") (OWNER " + _owner + ") )";
    }
    
    
}
