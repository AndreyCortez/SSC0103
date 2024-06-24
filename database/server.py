import json
from http.server import BaseHTTPRequestHandler, HTTPServer
from urllib.parse import urlparse, parse_qs
import tabela


class SimpleHTTPRequestHandler(BaseHTTPRequestHandler):
    def do_GET(self):
        tab = tabela.Tabela()
        parsed_path = urlparse(self.path)
        query = parse_qs(parsed_path.query)

        op = query.get('operation', [''])[0]
        table = query.get('table', [''])[0]
        tab.carregar(table)

        response_json = json.dumps([])
        print(op)
        print(table)
        if op == 'disponiveis':
            response_json = tab.disponiveis() 
        elif op == 'todos':
            response_json = tab.todos_itens()
        elif op == 'buscar':
            col = query.get('col', [])
            val_col = query.get('val_col', [])
            print(col)
            print(val_col)
            response_json = tab.procurar_item(col, val_col)
        elif op == 'apagar':
            col = query.get('col', [])
            val_col = query.get('val_col', [])
            response_json = tab.deletar_item(col, val_col)
        elif op == 'inserir':
            val = query.get('val', [])
            print(val)
            response_json = tab.inserir_item(val)

        self.send_response(200)

        self.send_header('Content-type', 'application/json')
        self.end_headers()

        print(response_json)
        self.wfile.write(response_json.encode('utf-8'))

def run(server_class=HTTPServer, handler_class=SimpleHTTPRequestHandler, port=8080):
    server_address = ('', port)
    httpd = server_class(server_address, handler_class)
    print(f'Starting httpd server on port {port}')
    httpd.serve_forever()

if __name__ == '__main__':
    run()
