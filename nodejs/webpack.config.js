module.exports = {
  entry: { // 엔트리 파일 목록
    app: '/Users/HY/IdeaProjects/NToon/nodejs/index.js'
  },
  output: {
    path: '/Users/HY/IdeaProjects/NToon/static/js', // 번들 파일 폴더
    filename: '[name].bundle.js' // 번들 파일 이름 규칙
  },
  module: {
    rules: [
      {
        test: /\.js$/,
        include: __dirname + "/service",
        exclude: /(node_modules)|(dist)/,
        use: {
          loader: 'babel-loader',
          options: {
            presets:[
              [
                "@babel/preset-env", {
                "targets": {"chrome": "55"}, /* chrome 55 이상으로 지정 */
                "debug": true
              }
              ]
            ]
          }
        }
      }
    ]
  }
}
