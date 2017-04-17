package info.androidhive.loginandregistration.activity;

/**
 * Created by Dewan Golam Mostafa on 06-Apr-17.
 */

public class TourItems {

    private String _name;
    private String _place;
    private String _start_date;
    private int _num_days;
    private int _budget;
    private String _moderator;
    private String _contact;
    private String _details;


    public TourItems(String name, String place,String start_date,int num_days,int budget,
                     String moderator,String contact,String details){

        _name = name;
        _place = place;
        _start_date = start_date;
        _num_days = num_days;
        _budget = budget;
        _moderator = moderator;
        _contact = contact;
        _details = details;

    }

    @Override
    public String toString() {
        String res = "";
        res =res+"Name: "+_name+"\n";
        res =res+"Place: "+_place+"\n";
        res =res+"Start date: "+_start_date+"\n";
        res =res+"Number of days: "+_num_days+"\n";
        res =res+"Budget: "+_budget+"\n";
        res =res+"Moderator: "+_moderator+"\n";
        res =res+"Contact: "+_contact+"\n";
        res =res+"Details: "+_details;

        return  res;
    }

    public String get_name() {
        return _name;
    }

    public String get_place() {
        return _place;
    }

    public String get_start_date() {
        return _start_date;
    }

    public int get_num_days() {
        return _num_days;
    }

    public int get_budget() {
        return _budget;
    }

    public String get_moderator() {
        return _moderator;
    }

    public String get_contact() {
        return _contact;
    }

    public String get_details() {
        return _details;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public void set_place(String _place) {
        this._place = _place;
    }

    public void set_start_date(String _start_date) {
        this._start_date = _start_date;
    }

    public void set_num_days(int _num_days) {
        this._num_days = _num_days;
    }

    public void set_budget(int _budget) {
        this._budget = _budget;
    }

    public void set_moderator(String _moderator) {
        this._moderator = _moderator;
    }

    public void set_contact(String _contact) {
        this._contact = _contact;
    }

    public void set_details(String _details) {
        this._details = _details;
    }
}
