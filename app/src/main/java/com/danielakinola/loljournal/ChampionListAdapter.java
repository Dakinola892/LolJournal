package com.danielakinola.loljournal;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Guest on 27/12/2017.
 */

public class ChampionListAdapter extends RecyclerView.Adapter<ChampionViewHolder> {

    String[] champNames = {"Aatrox", "Ahri", "Akali", "Alistar", "Amumu", "Anivia", "Annie", "Ashe",
            "AurelionSol", "Azir", "Bard", "Blitzcrank", "Brand", "Braum", "Caitlyn", "Camille",
            "Cassiopeia", "Cho'Gath", "Corki", "Darius", "Diana", "DrMundo", "Draven", "Ekko", "Elise",
            "Evelynn", "Ezreal", "Fiddlesticks", "Fiora", "Fizz", "Galio", "Gangplank", "Garen",
            "Gnar", "Gragas", "Graves", "Hecarim", "Heimerdinger", "Illaoi", "Irelia", "Ivern", "Janna",
            "JarvanIV", "Jax", "Jayce", "Jhin", "Jinx", "Kalista", "Karma", "Karthus", "Kassadin",
            "Katarina", "Kayn", "Kayle", "Kennen", "Kled", "Kindred", "KhaZix", "KogMaw", "LeBlanc",
            "Lee Sin", "Leona", "Lissandra", "Lucian", "Lulu", "Lux", "Malphite", "Malzahar", "Maokai",
            "Master Yi", "MissFortune", "Mordekaiser", "Morgana", "Nami", "Nasus", "Nautilus", "Nidalee",
            "Nocturne", "Nunu", "Olaf", "Orianna", "Ornn", "Pantheon", "Poppy", "Quinn", "Rakan",
            "Rammus", "RekSai", "Renekton", "Rengar", "Riven", "Rumble", "Ryze", "Sejuani", "Shaco",
            "Shen", "Shyvana", "Singed", "Sion", "Sivir", "Skarner", "Sona", "Soraka", "Swain", "Syndra",
            "TahmKench", "Taliyah", "Talon", "Taric", "Teemo", "Thresh", "Tristana", "Trundle",
            "Tryndamere", "TwistedFate", "Twitch", "Udyr", "Urgot", "Varus", "Vayne", "Veigar",
            "VelKoz", "Vi", "Viktor", "Vladimir", "Volibear", "Warwick", "Wukong", "Xayah", "Xerath",
            "Xin Zhao", "Yasuo", "Yorick", "Zac", "Zed", "Ziggs", "Zoe", "Zilean", "Zyra"};
    ArrayList<String> chosenChampions = new ArrayList<>();
    private int[] championSquares = {
            R.drawable.aatrox_square_0, R.drawable.ahri_square_0, R.drawable.akali_square_0,
            R.drawable.alistar_square_0, R.drawable.amumu_square_0, R.drawable.anivia_square_0,
            R.drawable.annie_square_0, R.drawable.ashe_square_0, R.drawable.aurelionsol_square_0,
            R.drawable.azir_square_0, R.drawable.bard_square_0, R.drawable.blitzcrank_square_0,
            R.drawable.brand_square_0, R.drawable.braum_square_0, R.drawable.caitlyn_square_0,
            R.drawable.camille_square_0, R.drawable.cassiopeia_square_0, R.drawable.chogath_square_0,
            R.drawable.corki_square_0, R.drawable.darius_square_0, R.drawable.diana_square_0,
            R.drawable.drmundo_square_0, R.drawable.draven_square_0, R.drawable.ekko_square_0,
            R.drawable.elise_square_0, R.drawable.evelynn_square_0, R.drawable.ezreal_square_0,
            R.drawable.fiddlesticks_square_0, R.drawable.fiora_square_0, R.drawable.fizz_square_0,
            R.drawable.galio_square_0, R.drawable.gangplank_square_0, R.drawable.garen_square_0,
            R.drawable.gnar_square_0, R.drawable.gragas_square_0, R.drawable.graves_square_0,
            R.drawable.hecarim_square_0, R.drawable.heimerdinger_square_0, R.drawable.illaoi_square_0,
            R.drawable.irelia_square_0, R.drawable.ivern_square_0, R.drawable.janna_square_0,
            R.drawable.jarvaniv_square_0, R.drawable.jax_square_0, R.drawable.jayce_square_0,
            R.drawable.jhin_square_0, R.drawable.jinx_square_0, R.drawable.kalista_square_0,
            R.drawable.karma_square_0, R.drawable.karthus_square_0, R.drawable.kassadin_square_0,
            R.drawable.katarina_square_0, R.drawable.kayn_square_0, R.drawable.kayle_square_0,
            R.drawable.kennen_square_0, R.drawable.kled_square_0, R.drawable.kindred_square_0,
            R.drawable.khazix_square_0, R.drawable.kogmaw_square_0, R.drawable.leblanc_square_0,
            R.drawable.leesin_square_0, R.drawable.leona_square_0, R.drawable.lissandra_square_0,
            R.drawable.lucian_square_0, R.drawable.lulu_square_0, R.drawable.lux_square_0,
            R.drawable.malphite_square_0, R.drawable.malzahar_square_0, R.drawable.maokai_square_0,
            R.drawable.masteryi_square_0, R.drawable.missfortune_square_0, R.drawable.mordekaiser_square_0,
            R.drawable.morgana_square_0, R.drawable.nami_square_0, R.drawable.nasus_square_0,
            R.drawable.nautilus_square_0, R.drawable.nidalee_square_0, R.drawable.nocturne_square_0,
            R.drawable.nunu_square_0, R.drawable.olaf_square_0, R.drawable.orianna_square_0,
            R.drawable.ornn_square_0, R.drawable.pantheon_square_0, R.drawable.poppy_square_0,
            R.drawable.quinn_square_0, R.drawable.rakan_square_0, R.drawable.rammus_square_0,
            R.drawable.reksai_square_0, R.drawable.renekton_square_0, R.drawable.rengar_square_0,
            R.drawable.riven_square_0, R.drawable.rumble_square_0, R.drawable.ryze_square_0,
            R.drawable.sejuani_square_0, R.drawable.shaco_square_0, R.drawable.shen_square_0,
            R.drawable.shyvana_square_0, R.drawable.singed_square_0, R.drawable.sion_square_0,
            R.drawable.sivir_square_0, R.drawable.skarner_square_0, R.drawable.sona_square_0,
            R.drawable.soraka_square_0, R.drawable.swain_square_0, R.drawable.syndra_square_0,
            R.drawable.tahmkench_square_0, R.drawable.taliyah_square_0, R.drawable.talon_square_0,
            R.drawable.taric_square_0, R.drawable.teemo_square_0, R.drawable.thresh_square_0,
            R.drawable.tristana_square_0, R.drawable.trundle_square_0, R.drawable.tryndamere_square_0,
            R.drawable.twistedfate_square_0, R.drawable.twitch_square_0, R.drawable.udyr_square_0,
            R.drawable.urgot_square_0, R.drawable.varus_square_0, R.drawable.vayne_square_0,
            R.drawable.veigar_square_0, R.drawable.velkoz_square_0, R.drawable.vi_square_0,
            R.drawable.viktor_square_0, R.drawable.vladimir_square_0, R.drawable.volibear_square_0,
            R.drawable.warwick_square_0, R.drawable.monkeyking_square_0, R.drawable.xayah_square_0,
            R.drawable.xerath_square_0, R.drawable.xinzhao_square_0, R.drawable.yasuo_square_0,
            R.drawable.yorick_square_0, R.drawable.zac_square_0, R.drawable.zed_square_0,
            R.drawable.ziggs_square_0, R.drawable.zoe_square_0, R.drawable.zilean_square_0,
            R.drawable.zyra_square_0};

    public ChampionListAdapter() {

    }

    public ArrayList<String> getChosenChampions() {
        return chosenChampions;
    }

    @Override
    public ChampionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.champion_list_item, parent, false);
        final ChampionViewHolder championListViewHolder = new ChampionViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String champName = champNames[championListViewHolder.getAdapterPosition()];
                if (championListViewHolder.textView.getVisibility() == View.GONE) {
                    championListViewHolder.textView.setVisibility(View.VISIBLE);
                    chosenChampions.add(champName);
                } else {
                    championListViewHolder.textView.setVisibility(View.GONE);
                    chosenChampions.remove(champName);
                }
            }
        });
        return championListViewHolder;
    }

    @Override
    public void onBindViewHolder(ChampionViewHolder holder, int position) {
        holder.imageView.setImageResource(championSquares[position]);
        holder.textView.setText(champNames[position]);
    }

    @Override
    public int getItemCount() {
        return champNames.length;
    }
}

