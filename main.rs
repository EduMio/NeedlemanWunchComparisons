use std::cmp::max;

const BLOSSUM_MATRIX: [[i32;20];20] = [
    [4, -1, -2, -2,  0, -1, -1,  0, -2, -1, -1, -1, -1, -2, -1,  1,  0, -3, -2,  0],
    [-1, 5,  0, -2, -3,  1,  0, -2,  0, -3, -2,  2, -1, -3, -2, -1, -1, -3, -2, -3],
    [-2, 0,  6,  1, -3,  0,  0,  0,  1, -3, -4,  0, -2, -3, -2,  1,  0, -4, -2, -3],
    [-2,-2,  1,  6, -3,  0,  2, -1, -1, -3, -4, -1, -3, -3, -1,  0, -1, -4, -3, -3],
    [ 0,-3, -3, -3,  9, -3, -4, -3, -3, -1, -1, -3, -1, -2, -3, -1, -1, -2, -2, -1],
    [-1, 1,  0,  0, -3,  5,  2, -2,  0, -3, -2,  1,  0, -3, -1,  0, -1, -2, -1, -2],
    [-1, 0,  0,  2, -4,  2,  5, -2,  0, -3, -3,  1, -2, -3, -1,  0, -1, -3, -2, -2],
    [ 0,-2,  0, -1, -3, -2, -2,  6, -2, -4, -4, -2, -3, -3, -2,  0, -2, -2, -3, -3],
    [-2, 0,  1, -1, -3,  0,  0, -2,  8, -3, -3, -1, -2, -1, -2, -1, -2, -2,  2, -3],
    [-1,-3, -3, -3, -1, -3, -3, -4, -3,  4,  2, -3,  1,  0, -3, -2, -1, -3, -1,  3],
    [-1,-2, -4, -4, -1, -2, -3, -4, -3,  2,  4, -2,  2,  0, -3, -2, -1, -2, -1,  1],
    [-1, 2,  0, -1, -3,  1,  1, -2, -1, -3, -2,  5, -1, -3, -1,  0, -1, -3, -2, -2],
    [-1,-1, -2, -3, -1,  0, -2, -3, -2,  1,  2, -1,  5,  0, -2, -1, -1, -1, -1,  1],
    [-2,-3, -3, -3, -2, -3, -3, -3, -1,  0,  0, -3,  0,  6, -4, -2, -2,  1,  3, -1],
    [-1,-2, -2, -1, -3, -1, -1, -2, -2, -3, -3, -1, -2, -4,  7, -1, -1, -4, -3, -2],
    [ 1,-1,  1,  0, -1,  0,  0,  0, -1, -2, -2,  0, -1, -2, -1,  4,  1, -3, -2, -2],
    [ 0,-1,  0, -1, -1, -1, -1, -2, -2, -1, -1, -1, -1, -2, -1,  1,  5, -2, -2,  0],
    [-3,-3, -4, -4, -2, -2, -3, -2, -2, -3, -2, -3, -1,  1, -4, -3, -2, 11,  2, -3],
    [-2,-2, -2, -3, -2, -1, -2, -3,  2, -1, -1, -2, -1,  3, -3, -2, -2,  2,  7, -1],
    [ 0,-3, -3, -3, -1, -2, -2, -3, -3,  3,  1, -2,  1, -1, -2, -2,  0, -3, -1,  4]
];

const UPPER_TO_BLOSSUM: [i8;26] = [0, -1, 4, 3, 6, 13, 7, 8, 9, -1, 11, 10, 12, 2, -1, 14, 5, 1, 15, 16, -1, 19, 17, -1, 18, -1];
                                //[a,  b, c, d, e,  f, g, h, i,  j,  k,  l,  m, n,  o,  p, q, r,  s,  t,  u,  v,  w,  x,  y,  z]

fn blossum(char1: u8, char2: u8) -> i32 {
    // amino_acid_dict = {
    // 'A': 'Ala', 'R': 'Arg', 'N': 'Asn', 'D': 'Asp', 'C': 'Cys',
    // 'Q': 'Gln', 'E': 'Glu', 'G': 'Gly', 'H': 'His', 'I': 'Ile',
    // 'L': 'Leu', 'K': 'Lys', 'M': 'Met', 'F': 'Phe', 'P': 'Pro',
    // 'S': 'Ser', 'T': 'Thr', 'W': 'Trp', 'Y': 'Tyr', 'V': 'Val'
    // }
    let char1: u8 = char1 - 65;
    let char2: u8 = char2 - 65;
    let index_x: i8 = UPPER_TO_BLOSSUM[char1 as usize];
    let index_y: i8 = UPPER_TO_BLOSSUM[char2 as usize];

    // This will panic if you get -1 from UPPER_TO_BLOSSUM.
    BLOSSUM_MATRIX[index_x as usize][index_y as usize] 
}

fn needleman_wunsch(sequence1: String, sequence2: String, pen_gap: i32) -> i32 {
    let n = sequence1.chars().count();
    let m = sequence2.chars().count();
    let mut  dp_matrix = vec![vec![0;m + 1];n + 1];
    
    for i in 1..(n + 1){
        for j in 1..(m + 1){
            dp_matrix[i][j] = max(
                max(
                    dp_matrix[i-1][j] - pen_gap,
                    dp_matrix[i][j-1] - pen_gap
                ),
                dp_matrix[i-1][j-1] + blossum(sequence1.as_bytes()[i-1], sequence2.as_bytes()[j-1])
            );
        }
    }
    dp_matrix[n][m] 
}

fn main() {
    let mut args = std::env::args();
    let _program = args.next();
    let sequence1 = args.next().expect("Two sequences are expected.");
    let sequence2 = args.next().expect("Two sequences are expected.");
    let pen_gap = match args.next(){
        Some(x) => str::parse::<i32>(x.as_str()).expect("The optional third argument must be an integer."),
        None => 4,
    
    };
    needleman_wunsch(sequence1, sequence2, pen_gap);
   // println!("{x}");
}
