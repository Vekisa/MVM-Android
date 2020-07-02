package com.example.mvm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mvm.model.Product;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    ListView listview;
    ArrayList<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        listview = findViewById(R.id.listview);
        String selected_category = getIntent().getStringExtra("selected_category");

        if(selected_category.equals("Živa stoka")){
            products.clear();
            products.add(new Product("Bikovi", R.drawable.bikovi));
            products.add(new Product("Dvisk e", R.drawable.dviske));
            products.add(new Product("Jagnjad", R.drawable.jagnjad));
            products.add(new Product("Jarad", R.drawable.jarad));
            products.add(new Product("Junad", R.drawable.junad));
            products.add(new Product("Koze", R.drawable.koze));
            products.add(new Product("Krave", R.drawable.krave));
            products.add(new Product("Krmače", R.drawable.krmace));
            products.add(new Product("Ovce", R.drawable.ovce));
            products.add(new Product("Ovnovi", R.drawable.ovnovi));
            products.add(new Product("Prasad (<15kg)", R.drawable.prasad_laksi));
            products.add(new Product("Prasad (>15kg)", R.drawable.prasad_tezi));
            products.add(new Product("Junice", R.drawable.junice));
            products.add(new Product("Kokoške", R.drawable.kokoske));
            products.add(new Product("Nazimice", R.drawable.nazimice));
            products.add(new Product("Šilježad", R.drawable.siljezad));
            products.add(new Product("Telad", R.drawable.telad));
            products.add(new Product("Tovljenici", R.drawable.tovljenici));
        }
        else if(selected_category.equals("Žitarice")){
            products.clear();
            products.add(new Product("Kukuruz u zrnu", R.drawable.kukuruz_u_zrnu));
            products.add(new Product("Kukuruz u klipu", R.drawable.kukuruz_u_klipu));
            products.add(new Product("Lucerka u balama", R.drawable.lucerka_u_balama));
            products.add(new Product("Pšenica u džaku", R.drawable.psenica_u_dzaku));
            products.add(new Product("Pšenica u rinfuzi", R.drawable.psenica_u_rinfuzi));
            products.add(new Product("Sojina sačma", R.drawable.sojina_sacma));
            products.add(new Product("Sojino zrno", R.drawable.sojino_zrno));
            products.add(new Product("Stočni ječam", R.drawable.stocni_jecam));
            products.add(new Product("Stočno brašno", R.drawable.stocno_brasno));
            products.add(new Product("Suncokret u zrnu", R.drawable.suncokret_u_zrnu));
            products.add(new Product("Suncokretova sačma", R.drawable.suncokretova_sacma));
        }
        else if(selected_category.equals("Jaja i živinsko meso")){
            products.clear();
            products.add(new Product("Jaja", R.drawable.jaja));
            products.add(new Product("Piletina", R.drawable.piletina));
        }
        else if(selected_category.equals("Mlečni proizvodi")){
            products.clear();
            products.add(new Product("Sir", R.drawable.sir));
            products.add(new Product("Kajmak", R.drawable.kajmak));
        }
        else if(selected_category.equals("Voće")){
            products.clear();
            products.add(new Product("Breskva", R.drawable.breskva));
            products.add(new Product("Jabuka", R.drawable.jabuka));
            products.add(new Product("Kajsija", R.drawable.kajsija));
            products.add(new Product("Kruška", R.drawable.kruska));
            products.add(new Product("Limun", R.drawable.limun));
            products.add(new Product("Višnja", R.drawable.visnja));
        }
        else if(selected_category.equals("Povrće")){
            products.clear();
            products.add(new Product("Blitva", R.drawable.blitva));
            products.add(new Product("Boranija", R.drawable.boranija));
            products.add(new Product("Brokoli", R.drawable.brokoli));
            products.add(new Product("Celer", R.drawable.celer));
            products.add(new Product("Cvekla", R.drawable.cvekla));
            products.add(new Product("Dinja", R.drawable.dinja));
            products.add(new Product("Karfiol", R.drawable.karfiol));
            products.add(new Product("Kelj", R.drawable.kelj));
            products.add(new Product("Krastavac", R.drawable.krastavac));
            products.add(new Product("Krompir", R.drawable.krompir));
            products.add(new Product("Kupus", R.drawable.kupus));
            products.add(new Product("Lubenica", R.drawable.lubenica));
            products.add(new Product("Beli luk", R.drawable.beli_luk));
            products.add(new Product("Crni luk", R.drawable.crni_luk));
            products.add(new Product("Paprika", R.drawable.paprika));
            products.add(new Product("Paradajz", R.drawable.paradajz));
            products.add(new Product("Pasulj", R.drawable.pasulj));
            products.add(new Product("Patlidžan", R.drawable.patlidzan));
            products.add(new Product("Peršun", R.drawable.persun));
            products.add(new Product("Pečurke", R.drawable.pecurke));
            products.add(new Product("Praziluk", R.drawable.praziluk));
            products.add(new Product("Ren", R.drawable.ren));
            products.add(new Product("Rotkva", R.drawable.rotkva));
            products.add(new Product("Rotkvica", R.drawable.rotkvica));
            products.add(new Product("Spanać", R.drawable.spanac));
            products.add(new Product("Tikvice", R.drawable.tikvice));
            products.add(new Product("Zelen", R.drawable.zelen));
            products.add(new Product("Zelena salata", R.drawable.zelena_salata));
            products.add(new Product("Zelje", R.drawable.zelje));
            products.add(new Product("Šargarepa", R.drawable.sargarepa));
            products.add(new Product("Špargla", R.drawable.spargla));
        }

        ProductAdapter adapter = new ProductAdapter(this, products, (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent listViewIntent = new Intent(getApplicationContext(), Map.class);
                startActivity(listViewIntent);
            }

        });
    }
}
