package br.com.ftec.julio.livros;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoDeDados extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    private static final String NOME_BANCO = "banco.db";
    public static final String TABELA = "livros";
    public static final String ID = "_id";
    public  static final String TITULO = "titulo";
    public static final String AUTOR = "autor";
    public static final String EDITORA = "editora";
    private static final int VERSAO = 1;

    public BancoDeDados(Context context) {
        super(context, NOME_BANCO,null,VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //esse código é usado para criar o banco de dados
        String sql = "CREATE TABLE if not exists "+TABELA+"(" + ID + " integer primary key autoincrement," + TITULO + " text," + AUTOR + " text," + EDITORA + " text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //caso a versão do banco de dados mude, podemos executar um SQL aqui
        db.execSQL("DROP TABLE IF EXISTS" + TABELA);
        onCreate(db);
    }

    public boolean inserirRegistro(String sTitulo, String sAutor, String sEditora) {
        ContentValues valores;

        db = getWritableDatabase();
        valores = new ContentValues();
        valores.put(TITULO, sTitulo);
        valores.put(AUTOR, sAutor);
        valores.put(EDITORA, sEditora);

        long resultado = db.insert(TABELA, null, valores);
        db.close();

        if (resultado == -1)
            return false;
        else
            return true;
    }

    public int deletarRegistro(int id){
        //deleta o registro e retorna o contador de linhas deletadas
        String where = ID + "=" + id;
        db = getReadableDatabase();
        int count = db.delete(TABELA, where, null);
        db.close();
        return  count;
    }

    public int alterarRegistro(int id, String titulo, String autor, String editora){
        ContentValues valores;
        String where;

        db = getWritableDatabase();

        where = ID + "=" + id;

        valores = new ContentValues();
        valores.put(TITULO, titulo);
        valores.put(AUTOR, autor);
        valores.put(EDITORA, editora);

        int count = db.update(TABELA, valores, where, null);
        db.close();
        return count;
    }

    public Cursor carregarDados(){
        Cursor cursor;
        String[] campos =  {ID,TITULO};
        db = getReadableDatabase();
        cursor = db.query(TABELA, campos, null, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor carregarDadoById(int id){
        Cursor cursor;
        String[] campos =  {ID,TITULO,AUTOR,EDITORA};
        String where = ID + "=" + id;
        db = getReadableDatabase();
        cursor = db.query(TABELA,campos,where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor consultaPorColuna(String sColuna, String sValor){
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA + " WHERE " + sColuna + " = ? ", new String[]{sValor});
        cursor.moveToFirst();
        db.close();

        return cursor;
    }
}


