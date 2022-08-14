import Data.List (transpose)
import Data.Time.Clock

matrixMultiply :: [[Int]] -> [[Int]] -> [[Int]]
-- $为函数应用符，用于降低函数优先级，具有右结合性
-- transpose：矩阵转置
matrixMultiply a b = [ [ sum $ zipWith (*) x y | y <- transpose b] | x <- a ]

readMatrix :: [String] -> [[Int]]
readMatrix a = f a
    where
        f = map $ map read . words


main = do
    -- 计时
    start <- getCurrentTime

    -- 输入模块
    let file_in = "data.txt"
    content <- readFile file_in
    let line = lines content
        n = read (head line) :: Int
        a = tail $ take (n + 1) line
        b = drop (n + 2) line

    -- 运算模块
    let matrix1 = readMatrix a
        matrix2 = readMatrix b
        matrix3 = matrixMultiply matrix1 matrix2

    -- 输出模块
    -- unwords函数将输入的列表连接成字符串
    let temp = map (unwords . map show) matrix3
        out = unlines temp
        file_out = "result_haskell.txt"
    writeFile file_out out

    --计时
    end <- getCurrentTime
    print $ diffUTCTime end start
