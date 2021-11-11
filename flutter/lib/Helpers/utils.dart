import 'package:flutter/cupertino.dart';
import 'package:football_area/Domain/match.dart';

class Utils{
  static List<Match> getMockedMatches()
  {return[
    Match(1,"Dinamo","FCSB","Stadionul Dinamo",'dinamo_vs_fcsb',"10.10.2021",30000,10),
    Match(2,"Farul Constanta","CSU Craiova","Stadionul Farul","farul_vs_craiova","10.10.2021",30000,20),
    Match(3,"Gaz Metan","UTA","Stadionul Gaz Metan Medias","gaz_vs_uta","10.10.2021",30000,15),
    Match(4,"CFR Cluj","FC Rapid","Stadionul Dr. Constantin Rădulescu","cfr_vs_rapid","10.10.2021",30000,30),
    Match(5,"Academica Clinceni","Sepsi","Stadionul Clinceni","clinceni_vs_sepsi","10.10.2021",30000,5),
    ];
  }
}