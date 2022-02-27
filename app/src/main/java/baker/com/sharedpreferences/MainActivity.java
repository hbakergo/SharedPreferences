package baker.com.sharedpreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    //vamos colocar no nome do arquivo em uma constante, normamente no nome usamos o padrão
    //de assinatura do nosso aplicativo, o nome fica grande para que seja único
    private static final String ARQUIVO =
            "com.baker.sharedpreferences.PREFERENCIAS_CORES";
    private int opcao = Color.BLUE; //opção de cor padrão, caso o usuário não tenha nenuma salva
    private static final String COR = "COR"; //chave usada na hora de recuperar/armazenar cor

    private ConstraintLayout layout; //usado para mapear o layout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setTheme(R.style.darkTheme);  //mudar para tema noturno

        setContentView(R.layout.activity_main);

        //mapear o layout
        layout = findViewById(R.id.layoutPrincipal);

        lerPreferenciaCor();

    }

    //ler para verificar se tem alguma preferência de cor salva no dispositivo que foi
    //escolhida pelo usuário
    private void lerPreferenciaCor(){

        //como estou dando nome pro arquivo, ele pode ser aberto por outras activities
        //na primeira vez que chama o getSharedPreferences, se o arquivo não existe, ele é criado
        SharedPreferences shared = getSharedPreferences(ARQUIVO,
                                                        Context.MODE_PRIVATE);

        opcao = shared.getInt(COR, opcao); //se não encontrar a chave cor, ele joga o default opcao

        mudaCorFundo();  //chama o método para setar a preferencia do usuáiro: mudar a cor do fundo

    }

    private void salvarPreferenciaCor(int novoValor){

        //primeiro: abrir o arquivo com as preferencias para escrita
        SharedPreferences shared = getSharedPreferences(ARQUIVO,
                                                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit(); //cria o editor para guardar o valor

        //para cada valor que eu quiser guardar no sharedPreferences, eu preciso chamar um put
        //nesse caso só chamou uma vez, pois muda só a cor e para pegar valor eu uso "get"
        editor.putInt(COR, novoValor);

        editor.commit(); //guarda no dispositivo do usuário as mudanças

        opcao = novoValor;
        mudaCorFundo();


    }

    private void mudaCorFundo(){
        layout.setBackgroundColor(opcao);
    }

    //chamado apenas a primeira vez que o menu é criado
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    //preparar o menu opções para exibição, é chamado toda vez que o menu vai ser exibito
    //aqui eu posso personalizar o menu de forma dinâmica via programação
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item;

        switch (opcao){
            case Color.YELLOW:
                item = menu.findItem(R.id.menuItemAmarelo);
                break;

            case Color.BLUE:
                item = menu.findItem(R.id.menuItemAzul);
                break;

            case Color.RED:
                item = menu.findItem(R.id.menuItemVermelho);
                break;

            default:
                return false;

        }
        item.setChecked(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        item.setChecked(true); //mostrar o item selecionado marcado na tela do usuário

        switch (item.getItemId()){
            case R.id.menuItemAmarelo:
                salvarPreferenciaCor(Color.YELLOW);
                return true;
            case R.id.menuItemAzul:
                salvarPreferenciaCor(Color.BLUE);
                return true;
            case R.id.menuItemVermelho:
                salvarPreferenciaCor(Color.RED);
                return true;
            default:
                return super.onOptionsItemSelected(item); //chama o pai
        }

    }
}